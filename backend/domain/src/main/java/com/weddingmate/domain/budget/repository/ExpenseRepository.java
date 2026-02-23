package com.weddingmate.domain.budget.repository;

import com.weddingmate.domain.budget.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserIdAndExpenseDateBetween(Long userId, LocalDate from, LocalDate to);
}
