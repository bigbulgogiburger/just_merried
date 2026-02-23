package com.weddingmate.api.budget.dto;

public record BudgetCategoryUpdateRequest(
        String name,
        Long plannedAmount,
        Long spentAmount,
        Integer sortOrder
) {
}
