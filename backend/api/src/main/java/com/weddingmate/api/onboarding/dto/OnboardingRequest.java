package com.weddingmate.api.onboarding.dto;

import com.weddingmate.domain.user.entity.WeddingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record OnboardingRequest(
        @NotNull WeddingStatus weddingStatus,
        LocalDate weddingDate,
        @NotBlank String region,
        List<String> interests
) {
}
