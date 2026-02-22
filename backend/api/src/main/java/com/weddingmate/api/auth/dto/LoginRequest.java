package com.weddingmate.api.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Access token is required")
        String accessToken
) {
}
