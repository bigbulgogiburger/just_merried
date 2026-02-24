package com.weddingmate.domain.market.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import com.weddingmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "secondhand_products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SecondhandProduct extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_user_id", nullable = false)
    private User sellerUser;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private long price;

    @Column(nullable = false, length = 80)
    private String region;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_status", nullable = false, length = 20)
    private SecondhandConditionStatus conditionStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_method", nullable = false, length = 20)
    private SecondhandTradeMethod tradeMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "sale_status", nullable = false, length = 20)
    private SecondhandSaleStatus saleStatus;

    @Builder
    public SecondhandProduct(User sellerUser, String title, String description, long price,
                             String region, SecondhandConditionStatus conditionStatus,
                             SecondhandTradeMethod tradeMethod, SecondhandSaleStatus saleStatus) {
        this.sellerUser = sellerUser;
        this.title = title;
        this.description = description;
        this.price = price;
        this.region = region;
        this.conditionStatus = conditionStatus == null ? SecondhandConditionStatus.GOOD : conditionStatus;
        this.tradeMethod = tradeMethod == null ? SecondhandTradeMethod.BOTH : tradeMethod;
        this.saleStatus = saleStatus == null ? SecondhandSaleStatus.ON_SALE : saleStatus;
    }
}
