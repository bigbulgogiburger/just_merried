package com.weddingmate.api.budget.dto;

import java.time.LocalDate;

public record BudgetUpsertRequest(
        Long totalBudget,
        String currency,
        LocalDate startDate,
        LocalDate endDate
) {
}
