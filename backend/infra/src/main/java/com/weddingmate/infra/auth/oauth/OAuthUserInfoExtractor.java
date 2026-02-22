package com.weddingmate.infra.auth.oauth;

import com.weddingmate.domain.user.entity.OAuthProvider;

import java.util.Map;

public final class OAuthUserInfoExtractor {

    private OAuthUserInfoExtractor() {
    }

    public static OAuthUserInfo extract(OAuthProvider provider, Map<String, Object> attributes) {
        return switch (provider) {
            case KAKAO -> extractKakao(attributes);
            case NAVER -> extractNaver(attributes);
            case GOOGLE -> extractGoogle(attributes);
            case APPLE -> extractApple(attributes);
        };
    }

    @SuppressWarnings("unchecked")
    private static OAuthUserInfo extractKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return new OAuthUserInfo(
                OAuthProvider.KAKAO,
                attributes.get("id").toString(),
                (String) kakaoAccount.get("email"),
                (String) profile.get("nickname"),
                (String) profile.get("profile_image_url")
        );
    }

    @SuppressWarnings("unchecked")
    private static OAuthUserInfo extractNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return new OAuthUserInfo(
                OAuthProvider.NAVER,
                (String) response.get("id"),
                (String) response.get("email"),
                (String) response.get("name"),
                (String) response.get("profile_image")
        );
    }

    private static OAuthUserInfo extractGoogle(Map<String, Object> attributes) {
        return new OAuthUserInfo(
                OAuthProvider.GOOGLE,
                (String) attributes.get("sub"),
                (String) attributes.get("email"),
                (String) attributes.get("name"),
                (String) attributes.get("picture")
        );
    }

    private static OAuthUserInfo extractApple(Map<String, Object> attributes) {
        return new OAuthUserInfo(
                OAuthProvider.APPLE,
                (String) attributes.get("sub"),
                (String) attributes.get("email"),
                null,
                null
        );
    }
}
