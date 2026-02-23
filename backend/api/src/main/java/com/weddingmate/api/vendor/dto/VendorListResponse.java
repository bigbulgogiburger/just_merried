package com.weddingmate.api.vendor.dto;

import com.weddingmate.domain.vendor.entity.Vendor;

import java.math.BigDecimal;

public record VendorListResponse(
        Long id,
        String name,
        String category,
        String region,
        long minPrice,
        long maxPrice,
        BigDecimal ratingAvg,
        int ratingCount,
        String status
) {
    public static VendorListResponse from(Vendor vendor) {
        return new VendorListResponse(
                vendor.getId(),
                vendor.getName(),
                vendor.getCategory().getName(),
                vendor.getRegion(),
                vendor.getMinPrice(),
                vendor.getMaxPrice(),
                vendor.getRatingAvg(),
                vendor.getRatingCount(),
                vendor.getStatus().name()
        );
    }
}
