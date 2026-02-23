package com.weddingmate.domain.vendor.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendor_prices")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VendorPrice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @Column(name = "package_name", nullable = false, length = 120)
    private String packageName;

    @Column(nullable = false)
    private long price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Builder
    public VendorPrice(Vendor vendor, String packageName, long price, String description, int sortOrder) {
        this.vendor = vendor;
        this.packageName = packageName;
        this.price = price;
        this.description = description;
        this.sortOrder = sortOrder;
    }
}
