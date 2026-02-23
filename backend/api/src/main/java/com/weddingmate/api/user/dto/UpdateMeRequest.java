package com.weddingmate.api.user.dto;

import com.weddingmate.domain.user.entity.WeddingStatus;

import java.time.LocalDate;

public record UpdateMeRequest(
        String nickname,
        String profileImageUrl,
        String region,
        WeddingStatus weddingStatus,
        LocalDate weddingDate
) {
}
