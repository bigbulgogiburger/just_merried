package com.weddingmate.infra.auth.oauth;

import com.weddingmate.domain.user.entity.OAuthProvider;

public record OAuthUserInfo(
        OAuthProvider provider,
        String providerId,
        String email,
        String nickname,
        String profileImageUrl
) {
}
