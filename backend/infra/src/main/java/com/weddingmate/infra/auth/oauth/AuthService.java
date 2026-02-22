package com.weddingmate.infra.auth.oauth;

import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.user.entity.OAuthProvider;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.entity.UserSettings;
import com.weddingmate.domain.user.repository.UserRepository;
import com.weddingmate.domain.user.repository.UserSettingsRepository;
import com.weddingmate.infra.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public JwtTokenProvider.TokenPair login(OAuthProvider provider, Map<String, Object> attributes) {
        OAuthUserInfo userInfo = OAuthUserInfoExtractor.extract(provider, attributes);

        User user = userRepository.findByProviderAndProviderId(provider, userInfo.providerId())
                .orElseGet(() -> createUser(userInfo));

        return generateTokenPair(user);
    }

    @Transactional(readOnly = true)
    public JwtTokenProvider.TokenPair refresh(String refreshToken) {
        return jwtTokenProvider.refreshTokens(refreshToken);
    }

    @Transactional
    public void logout(String accessToken, Long userId) {
        jwtTokenProvider.logout(accessToken, userId);
    }

    private User createUser(OAuthUserInfo userInfo) {
        String nickname = userInfo.nickname() != null
                ? userInfo.nickname()
                : "user_" + UUID.randomUUID().toString().substring(0, 8);

        // Handle nickname conflicts
        if (userRepository.existsByNickname(nickname)) {
            nickname = nickname + "_" + UUID.randomUUID().toString().substring(0, 4);
        }

        User user = User.builder()
                .email(userInfo.email())
                .nickname(nickname)
                .profileImageUrl(userInfo.profileImageUrl())
                .provider(userInfo.provider())
                .providerId(userInfo.providerId())
                .build();

        User savedUser = userRepository.save(user);

        // Create default user settings
        UserSettings settings = UserSettings.builder()
                .user(savedUser)
                .pushEnabled(true)
                .marketingAgreed(false)
                .build();
        userSettingsRepository.save(settings);

        log.info("New user created: id={}, provider={}", savedUser.getId(), userInfo.provider());
        return savedUser;
    }

    private JwtTokenProvider.TokenPair generateTokenPair(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(
                user.getId(), user.getEmail(), user.getRole().name());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());
        return new JwtTokenProvider.TokenPair(accessToken, refreshToken);
    }
}
