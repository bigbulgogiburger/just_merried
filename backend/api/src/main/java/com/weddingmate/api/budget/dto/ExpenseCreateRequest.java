package com.weddingmate.api.budget.dto;

import com.weddingmate.domain.budget.entity.PayerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ExpenseCreateRequest(
        @NotNull Long budgetCategoryId,
        @NotBlank String title,
        @NotNull Long amount,
        @NotNull LocalDate expenseDate,
        String memo,
        PayerType payerType
) {
}
