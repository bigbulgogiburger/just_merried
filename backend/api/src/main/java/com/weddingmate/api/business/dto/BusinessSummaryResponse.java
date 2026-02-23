package com.weddingmate.api.business.dto;

import com.weddingmate.domain.user.entity.BusinessProfile;

import java.time.LocalDateTime;

public record BusinessSummaryResponse(
        Long id,
        Long userId,
        String businessName,
        String businessNumber,
        String category,
        String status,
        LocalDateTime submittedAt,
        LocalDateTime approvedAt
) {
    public static BusinessSummaryResponse from(BusinessProfile profile) {
        return new BusinessSummaryResponse(
                profile.getId(),
                profile.getUser().getId(),
                profile.getBusinessName(),
                profile.getBusinessNumber(),
                profile.getCategory(),
                profile.getStatus().name(),
                profile.getSubmittedAt(),
                profile.getApprovedAt()
        );
    }
}
