package com.weddingmate.api.budget.dto;

import java.util.List;

public record BudgetSummaryResponse(
        Long budgetId,
        long totalBudget,
        long totalPlanned,
        long totalSpent,
        long totalRemaining,
        String currency,
        List<BudgetCategoryResponse> categories
) {
}
