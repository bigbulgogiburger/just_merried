package com.weddingmate.api.auth;

import com.weddingmate.api.auth.dto.LoginRequest;
import com.weddingmate.api.auth.dto.RefreshRequest;
import com.weddingmate.api.auth.dto.TokenResponse;
import com.weddingmate.common.exception.BusinessException;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.domain.user.entity.OAuthProvider;
import com.weddingmate.infra.auth.jwt.JwtTokenProvider;
import com.weddingmate.infra.auth.oauth.AuthService;
import com.weddingmate.infra.auth.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Auth", description = "Authentication API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Social login", description = "Login with OAuth provider (kakao, naver, google, apple)")
    @PostMapping("/login/{provider}")
    public ResponseEntity<ApiResponse<TokenResponse>> login(
            @PathVariable String provider,
            @Valid @RequestBody LoginRequest request) {
        OAuthProvider oauthProvider = parseProvider(provider);

        // The access token from the client is used to fetch user info from the provider
        // In a real implementation, this would call the provider's API to get user attributes
        Map<String, Object> attributes = Map.of("access_token", request.accessToken());

        JwtTokenProvider.TokenPair tokenPair = authService.login(oauthProvider, attributes);

        TokenResponse tokenResponse = new TokenResponse(
                tokenPair.accessToken(),
                tokenPair.refreshToken()
        );

        return ResponseEntity.ok(ApiResponse.ok("Login successful", tokenResponse));
    }

    @Operation(summary = "Refresh token", description = "Get new access token using refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refresh(
            @Valid @RequestBody RefreshRequest request) {
        JwtTokenProvider.TokenPair tokenPair = authService.refresh(request.refreshToken());

        TokenResponse tokenResponse = new TokenResponse(
                tokenPair.accessToken(),
                tokenPair.refreshToken()
        );

        return ResponseEntity.ok(ApiResponse.ok("Token refreshed", tokenResponse));
    }

    @Operation(summary = "Logout", description = "Invalidate current access and refresh tokens")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @RequestHeader("Authorization") String authorization,
            @AuthenticationPrincipal UserPrincipal principal) {
        String accessToken = authorization.replace("Bearer ", "");
        authService.logout(accessToken, principal.userId());
        return ResponseEntity.ok(ApiResponse.ok("Logout successful"));
    }

    private OAuthProvider parseProvider(String provider) {
        try {
            return OAuthProvider.valueOf(provider.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,
                    "Unsupported OAuth provider: " + provider);
        }
    }
}
