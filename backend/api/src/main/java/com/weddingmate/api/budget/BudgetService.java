package com.weddingmate.api.budget;

import com.weddingmate.api.budget.dto.*;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.budget.entity.Budget;
import com.weddingmate.domain.budget.entity.BudgetCategory;
import com.weddingmate.domain.budget.entity.Expense;
import com.weddingmate.domain.budget.repository.BudgetCategoryRepository;
import com.weddingmate.domain.budget.repository.BudgetRepository;
import com.weddingmate.domain.budget.repository.ExpenseRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BudgetCategoryRepository budgetCategoryRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @Transactional
    public BudgetSummaryResponse upsertBudget(Long userId, BudgetUpsertRequest request) {
        Budget budget = budgetRepository.findByUserId(userId)
                .map(existing -> {
                    existing.update(request.totalBudget(), request.currency(), request.startDate(), request.endDate());
                    return existing;
                })
                .orElseGet(() -> budgetRepository.save(Budget.builder()
                        .user(getUser(userId))
                        .totalBudget(request.totalBudget() != null ? request.totalBudget() : 0L)
                        .currency(request.currency())
                        .startDate(request.startDate())
                        .endDate(request.endDate())
                        .build()));

        return getSummary(userId);
    }

    @Transactional(readOnly = true)
    public BudgetSummaryResponse getSummary(Long userId) {
        Budget budget = getBudget(userId);
        List<BudgetCategoryResponse> categories = budgetCategoryRepository.findByBudgetIdOrderBySortOrderAsc(budget.getId())
                .stream()
                .map(BudgetCategoryResponse::from)
                .toList();

        long totalPlanned = categories.stream().mapToLong(BudgetCategoryResponse::plannedAmount).sum();
        long totalSpent = categories.stream().mapToLong(BudgetCategoryResponse::spentAmount).sum();

        return new BudgetSummaryResponse(
                budget.getId(),
                budget.getTotalBudget(),
                totalPlanned,
                totalSpent,
                Math.max(0, budget.getTotalBudget() - totalSpent),
                budget.getCurrency(),
                categories
        );
    }

    @Transactional
    public BudgetCategoryResponse createCategory(Long userId, BudgetCategoryCreateRequest request) {
        Budget budget = getBudget(userId);
        BudgetCategory category = budgetCategoryRepository.save(BudgetCategory.builder()
                .budget(budget)
                .name(request.name())
                .plannedAmount(request.plannedAmount() != null ? request.plannedAmount() : 0L)
                .spentAmount(0L)
                .sortOrder(request.sortOrder() != null ? request.sortOrder() : 0)
                .build());
        return BudgetCategoryResponse.from(category);
    }

    @Transactional
    public BudgetCategoryResponse updateCategory(Long userId, Long categoryId, BudgetCategoryUpdateRequest request) {
        BudgetCategory category = getOwnedCategory(userId, categoryId);
        category.update(request.name(), request.plannedAmount(), request.spentAmount(), request.sortOrder());
        return BudgetCategoryResponse.from(category);
    }

    @Transactional
    public void deleteCategory(Long userId, Long categoryId) {
        BudgetCategory category = getOwnedCategory(userId, categoryId);
        budgetCategoryRepository.delete(category);
    }

    @Transactional
    public ExpenseResponse createExpense(Long userId, ExpenseCreateRequest request) {
        BudgetCategory category = getOwnedCategory(userId, request.budgetCategoryId());
        Expense expense = expenseRepository.save(Expense.builder()
                .budgetCategory(category)
                .user(getUser(userId))
                .title(request.title())
                .amount(request.amount())
                .expenseDate(request.expenseDate())
                .memo(request.memo())
                .payerType(request.payerType())
                .build());

        category.increaseSpent(request.amount());
        return ExpenseResponse.from(expense);
    }

    @Transactional
    public ExpenseResponse updateExpense(Long userId, Long expenseId, ExpenseUpdateRequest request) {
        Expense expense = getOwnedExpense(userId, expenseId);
        BudgetCategory category = expense.getBudgetCategory();
        long before = expense.getAmount();
        expense.update(request.title(), request.amount(), request.expenseDate(), request.memo(), request.payerType());

        category.decreaseSpent(before);
        category.increaseSpent(expense.getAmount());
        return ExpenseResponse.from(expense);
    }

    @Transactional
    public void deleteExpense(Long userId, Long expenseId) {
        Expense expense = getOwnedExpense(userId, expenseId);
        expense.getBudgetCategory().decreaseSpent(expense.getAmount());
        expenseRepository.delete(expense);
    }

    @Transactional(readOnly = true)
    public List<ExpenseResponse> getExpenses(Long userId, LocalDate from, LocalDate to) {
        LocalDate fromDate = from != null ? from : LocalDate.now().minusMonths(1);
        LocalDate toDate = to != null ? to : LocalDate.now().plusDays(1);
        return expenseRepository.findByUserIdAndExpenseDateBetween(userId, fromDate, toDate)
                .stream()
                .map(ExpenseResponse::from)
                .toList();
    }

    private Budget getBudget(Long userId) {
        return budgetRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BUDGET_NOT_FOUND, "예산 정보를 찾을 수 없습니다."));
    }

    private BudgetCategory getOwnedCategory(Long userId, Long categoryId) {
        BudgetCategory category = budgetCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BUDGET_CATEGORY_NOT_FOUND, "예산 카테고리를 찾을 수 없습니다."));
        if (!category.getBudget().getUser().getId().equals(userId)) {
            throw new NotFoundException(ErrorCode.BUDGET_CATEGORY_NOT_FOUND, "예산 카테고리를 찾을 수 없습니다.");
        }
        return category;
    }

    private Expense getOwnedExpense(Long userId, Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EXPENSE_NOT_FOUND, "지출 내역을 찾을 수 없습니다."));
        if (!expense.getUser().getId().equals(userId)) {
            throw new NotFoundException(ErrorCode.EXPENSE_NOT_FOUND, "지출 내역을 찾을 수 없습니다.");
        }
        return expense;
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }
}
