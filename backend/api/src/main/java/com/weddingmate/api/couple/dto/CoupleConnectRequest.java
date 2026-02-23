package com.weddingmate.api.couple.dto;

import com.weddingmate.domain.couple.entity.CoupleRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CoupleConnectRequest(
        @NotBlank String inviteCode,
        @NotNull CoupleRole role
) {
}
