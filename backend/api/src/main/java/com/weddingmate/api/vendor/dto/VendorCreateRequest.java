package com.weddingmate.api.vendor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VendorCreateRequest(
        Long ownerUserId,
        @NotNull Long categoryId,
        @NotBlank String name,
        String description,
        @NotBlank String region,
        String address,
        String phone,
        String homepageUrl,
        Long minPrice,
        Long maxPrice
) {
}
