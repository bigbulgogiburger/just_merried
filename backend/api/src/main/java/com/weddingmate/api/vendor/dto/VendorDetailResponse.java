package com.weddingmate.api.vendor.dto;

import com.weddingmate.domain.vendor.entity.Vendor;
import com.weddingmate.domain.vendor.entity.VendorImage;
import com.weddingmate.domain.vendor.entity.VendorPrice;

import java.math.BigDecimal;
import java.util.List;

public record VendorDetailResponse(
        Long id,
        String name,
        String description,
        String category,
        String region,
        String address,
        String phone,
        String homepageUrl,
        long minPrice,
        long maxPrice,
        BigDecimal ratingAvg,
        int ratingCount,
        String status,
        List<ImageResponse> images,
        List<PriceResponse> prices
) {
    public record ImageResponse(Long id, String imageUrl, boolean isCover, int sortOrder) {
        public static ImageResponse from(VendorImage image) {
            return new ImageResponse(image.getId(), image.getImageUrl(), image.isCover(), image.getSortOrder());
        }
    }

    public record PriceResponse(Long id, String packageName, long price, String description, int sortOrder) {
        public static PriceResponse from(VendorPrice price) {
            return new PriceResponse(price.getId(), price.getPackageName(), price.getPrice(), price.getDescription(), price.getSortOrder());
        }
    }

    public static VendorDetailResponse of(Vendor vendor, List<ImageResponse> images, List<PriceResponse> prices) {
        return new VendorDetailResponse(
                vendor.getId(),
                vendor.getName(),
                vendor.getDescription(),
                vendor.getCategory().getName(),
                vendor.getRegion(),
                vendor.getAddress(),
                vendor.getPhone(),
                vendor.getHomepageUrl(),
                vendor.getMinPrice(),
                vendor.getMaxPrice(),
                vendor.getRatingAvg(),
                vendor.getRatingCount(),
                vendor.getStatus().name(),
                images,
                prices
        );
    }
}
