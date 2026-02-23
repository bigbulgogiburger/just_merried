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
@Table(name = "budgets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Budget extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "total_budget", nullable = false)
    private long totalBudget;

    @Column(nullable = false, length = 10)
    private String currency;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Builder
    public Budget(User user, long totalBudget, String currency, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.totalBudget = totalBudget;
        this.currency = currency != null ? currency : "KRW";
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
