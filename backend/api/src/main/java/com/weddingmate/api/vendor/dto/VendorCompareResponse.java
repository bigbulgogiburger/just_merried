package com.weddingmate.api.vendor.dto;

import com.weddingmate.domain.vendor.entity.CompareItem;

import java.math.BigDecimal;

public record VendorCompareResponse(
        Long compareItemId,
        Long vendorId,
        String name,
        String category,
        String region,
        long minPrice,
        long maxPrice,
        BigDecimal ratingAvg,
        int ratingCount
) {
    public static VendorCompareResponse from(CompareItem compareItem) {
        var vendor = compareItem.getVendor();
        return new VendorCompareResponse(
                compareItem.getId(),
                vendor.getId(),
                vendor.getName(),
                vendor.getCategory().getName(),
                vendor.getRegion(),
                vendor.getMinPrice(),
                vendor.getMaxPrice(),
                vendor.getRatingAvg(),
                vendor.getRatingCount()
        );
    }
}
