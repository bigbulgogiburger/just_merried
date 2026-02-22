package com.weddingmate.infra.auth.jwt;

import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.UnauthorizedException;
import com.weddingmate.infra.redis.RedisService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";
    private static final String BLACKLIST_PREFIX = "blacklist:";

    private final JwtProperties jwtProperties;
    private final RedisService redisService;
    private final ResourceLoader resourceLoader;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    public void init() {
        try {
            this.privateKey = loadPrivateKey(jwtProperties.getPrivateKeyPath());
            this.publicKey = loadPublicKey(jwtProperties.getPublicKeyPath());
        } catch (Exception e) {
            log.warn("Failed to load RSA keys, JWT signing will not be available: {}", e.getMessage());
        }
    }

    public String generateAccessToken(Long userId, String email, String role) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getAccessTokenExpiration());

        return Jwts.builder()
                .subject(userId.toString())
                .claim("email", email)
                .claim("role", role)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public String generateRefreshToken(Long userId) {
        String tokenId = UUID.randomUUID().toString();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getRefreshTokenExpiration());

        String refreshToken = Jwts.builder()
                .subject(userId.toString())
                .id(tokenId)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();

        // Store refresh token in Redis for rotation
        redisService.set(
                REFRESH_TOKEN_PREFIX + userId,
                tokenId,
                Duration.ofMillis(jwtProperties.getRefreshTokenExpiration())
        );

        return refreshToken;
    }

    public TokenInfo validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // Check if token is blacklisted
            String jti = claims.getId();
            if (jti != null && redisService.hasKey(BLACKLIST_PREFIX + jti)) {
                throw new UnauthorizedException(ErrorCode.INVALID_TOKEN, "Token has been revoked");
            }

            return new TokenInfo(
                    Long.parseLong(claims.getSubject()),
                    claims.get("email", String.class),
                    claims.get("role", String.class),
                    jti
            );
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(ErrorCode.TOKEN_EXPIRED);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException e) {
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN);
        } catch (UnauthorizedException e) {
            throw e;
        } catch (Exception e) {
            log.error("Token validation error", e);
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN);
        }
    }

    public TokenPair refreshTokens(String refreshToken) {
        TokenInfo tokenInfo = validateToken(refreshToken);
        Long userId = tokenInfo.userId();

        // Verify the refresh token matches the one stored in Redis (rotation)
        String storedTokenId = redisService.get(REFRESH_TOKEN_PREFIX + userId)
                .map(Object::toString)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if (!storedTokenId.equals(tokenInfo.tokenId())) {
            // Possible token reuse detected - invalidate all tokens for this user
            redisService.delete(REFRESH_TOKEN_PREFIX + userId);
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN, "Refresh token has been rotated");
        }

        // Blacklist old refresh token
        blacklistToken(tokenInfo.tokenId(), Duration.ofMillis(jwtProperties.getRefreshTokenExpiration()));

        // Generate new token pair
        String newAccessToken = generateAccessToken(userId, tokenInfo.email(), tokenInfo.role());
        String newRefreshToken = generateRefreshToken(userId);

        return new TokenPair(newAccessToken, newRefreshToken);
    }

    public void logout(String accessToken, Long userId) {
        try {
            TokenInfo tokenInfo = validateToken(accessToken);
            if (tokenInfo.tokenId() != null) {
                blacklistToken(tokenInfo.tokenId(), Duration.ofMillis(jwtProperties.getAccessTokenExpiration()));
            }
        } catch (UnauthorizedException ignored) {
            // Token might already be expired, that's fine
        }
        redisService.delete(REFRESH_TOKEN_PREFIX + userId);
    }

    private void blacklistToken(String tokenId, Duration ttl) {
        redisService.set(BLACKLIST_PREFIX + tokenId, "blacklisted", ttl);
    }

    private PrivateKey loadPrivateKey(String path) throws Exception {
        String keyContent = loadKeyContent(path);
        keyContent = keyContent
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(keyContent);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    private PublicKey loadPublicKey(String path) throws Exception {
        String keyContent = loadKeyContent(path);
        keyContent = keyContent
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");
        byte[] keyBytes = Base64.getDecoder().decode(keyContent);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    private String loadKeyContent(String path) throws Exception {
        try (InputStream is = resourceLoader.getResource(path).getInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public record TokenInfo(Long userId, String email, String role, String tokenId) {
    }

    public record TokenPair(String accessToken, String refreshToken) {
    }
}
