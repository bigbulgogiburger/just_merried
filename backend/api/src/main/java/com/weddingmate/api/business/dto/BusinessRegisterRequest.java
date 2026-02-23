package com.weddingmate.api.business.dto;

import jakarta.validation.constraints.NotBlank;

public record BusinessRegisterRequest(
        @NotBlank String businessName,
        @NotBlank String businessNumber,
        @NotBlank String category,
        String address,
        String phone,
        String description,
        String businessLicenseImageUrl
) {
}
