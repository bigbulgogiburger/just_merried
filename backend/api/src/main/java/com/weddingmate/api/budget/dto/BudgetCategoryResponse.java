package com.weddingmate.api.budget.dto;

import com.weddingmate.domain.budget.entity.BudgetCategory;

public record BudgetCategoryResponse(
        Long id,
        String name,
        long plannedAmount,
        long spentAmount,
        long remainingAmount,
        int sortOrder
) {
    public static BudgetCategoryResponse from(BudgetCategory category) {
        return new BudgetCategoryResponse(
                category.getId(),
                category.getName(),
                category.getPlannedAmount(),
                category.getSpentAmount(),
                Math.max(0, category.getPlannedAmount() - category.getSpentAmount()),
                category.getSortOrder()
        );
    }
}
