package com.weddingmate.domain.market.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "market_product_options")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarketProductOption extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private MarketProduct product;

    @Column(name = "option_name", nullable = false, length = 120)
    private String optionName;

    @Column(name = "extra_price", nullable = false)
    private long extraPrice;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Builder
    public MarketProductOption(MarketProduct product, String optionName, long extraPrice, int stockQuantity) {
        this.product = product;
        this.optionName = optionName;
        this.extraPrice = extraPrice;
        this.stockQuantity = stockQuantity;
    }
}
