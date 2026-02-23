package com.weddingmate.domain.vendor.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendor_categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VendorCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Builder
    public VendorCategory(String name, int sortOrder) {
        this.name = name;
        this.sortOrder = sortOrder;
    }
}
