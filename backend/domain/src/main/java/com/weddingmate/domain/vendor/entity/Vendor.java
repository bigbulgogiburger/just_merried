package com.weddingmate.domain.vendor.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import com.weddingmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "vendors")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vendor extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private User ownerUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private VendorCategory category;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 50)
    private String region;

    @Column(length = 255)
    private String address;

    @Column(length = 30)
    private String phone;

    @Column(name = "homepage_url", length = 500)
    private String homepageUrl;

    @Column(name = "min_price", nullable = false)
    private long minPrice;

    @Column(name = "max_price", nullable = false)
    private long maxPrice;

    @Column(name = "rating_avg", nullable = false, precision = 3, scale = 2)
    private BigDecimal ratingAvg;

    @Column(name = "rating_count", nullable = false)
    private int ratingCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VendorStatus status;

    @Builder
    public Vendor(User ownerUser, VendorCategory category, String name, String description,
                  String region, String address, String phone, String homepageUrl,
                  long minPrice, long maxPrice) {
        this.ownerUser = ownerUser;
        this.category = category;
        this.name = name;
        this.description = description;
        this.region = region;
        this.address = address;
        this.phone = phone;
        this.homepageUrl = homepageUrl;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.ratingAvg = BigDecimal.ZERO;
        this.ratingCount = 0;
        this.status = VendorStatus.ACTIVE;
    }
}
