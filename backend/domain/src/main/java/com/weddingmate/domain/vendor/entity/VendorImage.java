package com.weddingmate.domain.vendor.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendor_images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VendorImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "is_cover", nullable = false)
    private boolean cover;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Builder
    public VendorImage(Vendor vendor, String imageUrl, boolean cover, int sortOrder) {
        this.vendor = vendor;
        this.imageUrl = imageUrl;
        this.cover = cover;
        this.sortOrder = sortOrder;
    }
}
