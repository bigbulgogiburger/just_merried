package com.weddingmate.domain.market.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "secondhand_product_images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SecondhandProductImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secondhand_product_id", nullable = false)
    private SecondhandProduct secondhandProduct;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Builder
    public SecondhandProductImage(SecondhandProduct secondhandProduct, String imageUrl, int sortOrder) {
        this.secondhandProduct = secondhandProduct;
        this.imageUrl = imageUrl;
        this.sortOrder = sortOrder;
    }
}
