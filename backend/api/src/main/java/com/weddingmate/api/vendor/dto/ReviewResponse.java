package com.weddingmate.api.vendor.dto;

import com.weddingmate.domain.vendor.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long userId,
        int rating,
        String content,
        LocalDateTime createdAt
) {
    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getUser().getId(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}
