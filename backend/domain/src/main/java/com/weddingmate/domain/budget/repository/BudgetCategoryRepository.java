package com.weddingmate.domain.budget.repository;

import com.weddingmate.domain.budget.entity.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Long> {
    List<BudgetCategory> findByBudgetIdOrderBySortOrderAsc(Long budgetId);
}
