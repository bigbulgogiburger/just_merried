package com.weddingmate.api.user.dto;

import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.entity.WeddingStatus;

import java.time.LocalDate;

public record MeResponse(
        Long id,
        String email,
        String nickname,
        String profileImageUrl,
        WeddingStatus weddingStatus,
        LocalDate weddingDate,
        String region,
        String role,
        String grade
) {
    public static MeResponse from(User user) {
        return new MeResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getProfileImageUrl(),
                user.getWeddingStatus(),
                user.getWeddingDate(),
                user.getRegion(),
                user.getRole().name(),
                user.getGrade().name()
        );
    }
}
