package com.weddingmate.api.vendor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewCreateRequest(
        @NotNull @Min(1) @Max(5) Integer rating,
        String content
) {
}
