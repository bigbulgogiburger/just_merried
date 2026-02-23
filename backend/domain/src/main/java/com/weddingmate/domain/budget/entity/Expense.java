package com.weddingmate.domain.budget.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import com.weddingmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expense extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_category_id", nullable = false)
    private BudgetCategory budgetCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false)
    private long amount;

    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Enumerated(EnumType.STRING)
    @Column(name = "payer_type", nullable = false, length = 20)
    private PayerType payerType;

    @Builder
    public Expense(BudgetCategory budgetCategory, User user, String title, long amount,
                   LocalDate expenseDate, String memo, PayerType payerType) {
        this.budgetCategory = budgetCategory;
        this.user = user;
        this.title = title;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.memo = memo;
        this.payerType = payerType != null ? payerType : PayerType.TOGETHER;
    }

    public void update(String title, Long amount, LocalDate expenseDate, String memo, PayerType payerType) {
        if (title != null && !title.isBlank()) this.title = title;
        if (amount != null) this.amount = amount;
        if (expenseDate != null) this.expenseDate = expenseDate;
        if (memo != null) this.memo = memo;
        if (payerType != null) this.payerType = payerType;
    }
}
