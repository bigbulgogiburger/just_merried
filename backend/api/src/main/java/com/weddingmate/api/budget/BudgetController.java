package com.weddingmate.api.budget;

import com.weddingmate.api.budget.dto.*;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Budget", description = "예산 관리 API")
@RestController
@RequestMapping("/api/v1/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @Operation(summary = "내 예산 생성/수정")
    @PutMapping("/me")
    public ResponseEntity<ApiResponse<BudgetSummaryResponse>> upsert(
            @CurrentUser UserPrincipal principal,
            @RequestBody BudgetUpsertRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("예산 정보가 저장되었습니다.", budgetService.upsertBudget(principal.userId(), request)));
    }

    @Operation(summary = "내 예산 요약 조회")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<BudgetSummaryResponse>> summary(@CurrentUser UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.ok(budgetService.getSummary(principal.userId())));
    }

    @Operation(summary = "예산 카테고리 생성")
    @PostMapping("/categories")
    public ResponseEntity<ApiResponse<BudgetCategoryResponse>> createCategory(
            @CurrentUser UserPrincipal principal,
            @Valid @RequestBody BudgetCategoryCreateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("카테고리가 생성되었습니다.", budgetService.createCategory(principal.userId(), request)));
    }

    @Operation(summary = "예산 카테고리 수정")
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<BudgetCategoryResponse>> updateCategory(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long categoryId,
            @RequestBody BudgetCategoryUpdateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("카테고리가 수정되었습니다.", budgetService.updateCategory(principal.userId(), categoryId, request)));
    }

    @Operation(summary = "예산 카테고리 삭제")
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long categoryId
    ) {
        budgetService.deleteCategory(principal.userId(), categoryId);
        return ResponseEntity.ok(ApiResponse.ok("카테고리가 삭제되었습니다."));
    }

    @Operation(summary = "지출 내역 생성")
    @PostMapping("/expenses")
    public ResponseEntity<ApiResponse<ExpenseResponse>> createExpense(
            @CurrentUser UserPrincipal principal,
            @Valid @RequestBody ExpenseCreateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("지출이 등록되었습니다.", budgetService.createExpense(principal.userId(), request)));
    }

    @Operation(summary = "지출 내역 목록")
    @GetMapping("/expenses")
    public ResponseEntity<ApiResponse<List<ExpenseResponse>>> listExpenses(
            @CurrentUser UserPrincipal principal,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        return ResponseEntity.ok(ApiResponse.ok(budgetService.getExpenses(principal.userId(), from, to)));
    }

    @Operation(summary = "지출 내역 수정")
    @PutMapping("/expenses/{expenseId}")
    public ResponseEntity<ApiResponse<ExpenseResponse>> updateExpense(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long expenseId,
            @RequestBody ExpenseUpdateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("지출이 수정되었습니다.", budgetService.updateExpense(principal.userId(), expenseId, request)));
    }

    @Operation(summary = "지출 내역 삭제")
    @DeleteMapping("/expenses/{expenseId}")
    public ResponseEntity<ApiResponse<Void>> deleteExpense(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long expenseId
    ) {
        budgetService.deleteExpense(principal.userId(), expenseId);
        return ResponseEntity.ok(ApiResponse.ok("지출이 삭제되었습니다."));
    }
}
