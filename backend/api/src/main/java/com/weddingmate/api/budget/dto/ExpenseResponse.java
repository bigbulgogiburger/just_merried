package com.weddingmate.api.budget.dto;

import com.weddingmate.domain.budget.entity.Expense;

import java.time.LocalDate;

public record ExpenseResponse(
        Long id,
        Long budgetCategoryId,
        String title,
        long amount,
        LocalDate expenseDate,
        String memo,
        String payerType
) {
    public static ExpenseResponse from(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getBudgetCategory().getId(),
                expense.getTitle(),
                expense.getAmount(),
                expense.getExpenseDate(),
                expense.getMemo(),
                expense.getPayerType().name()
        );
    }
}
