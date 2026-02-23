package com.weddingmate.api.budget.dto;

import com.weddingmate.domain.budget.entity.PayerType;

import java.time.LocalDate;

public record ExpenseUpdateRequest(
        String title,
        Long amount,
        LocalDate expenseDate,
        String memo,
        PayerType payerType
) {
}
