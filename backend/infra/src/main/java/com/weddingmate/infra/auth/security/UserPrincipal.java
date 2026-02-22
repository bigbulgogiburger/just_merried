package com.weddingmate.infra.auth.security;

public record UserPrincipal(
        Long userId,
        String email,
        String role
) {
}
