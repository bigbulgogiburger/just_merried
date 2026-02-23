package com.weddingmate.api.budget.dto;

import jakarta.validation.constraints.NotBlank;

public record BudgetCategoryCreateRequest(
        @NotBlank String name,
        Long plannedAmount,
        Integer sortOrder
) {
}
