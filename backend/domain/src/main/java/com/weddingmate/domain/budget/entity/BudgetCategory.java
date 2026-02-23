package com.weddingmate.domain.budget.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "budget_categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BudgetCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id", nullable = false)
    private Budget budget;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(name = "planned_amount", nullable = false)
    private long plannedAmount;

    @Column(name = "spent_amount", nullable = false)
    private long spentAmount;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Builder
    public BudgetCategory(Budget budget, String name, long plannedAmount, long spentAmount, int sortOrder) {
        this.budget = budget;
        this.name = name;
        this.plannedAmount = plannedAmount;
        this.spentAmount = spentAmount;
        this.sortOrder = sortOrder;
    }

    public void update(String name, Long plannedAmount, Long spentAmount, Integer sortOrder) {
        if (name != null && !name.isBlank()) this.name = name;
        if (plannedAmount != null) this.plannedAmount = plannedAmount;
        if (spentAmount != null) this.spentAmount = spentAmount;
        if (sortOrder != null) this.sortOrder = sortOrder;
    }

    public void increaseSpent(long amount) {
        this.spentAmount += amount;
    }

    public void decreaseSpent(long amount) {
        this.spentAmount = Math.max(0, this.spentAmount - amount);
    }
}
