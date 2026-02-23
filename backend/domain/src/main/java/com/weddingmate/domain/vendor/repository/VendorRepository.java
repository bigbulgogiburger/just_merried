package com.weddingmate.domain.vendor.repository;

import com.weddingmate.domain.vendor.entity.Vendor;
import com.weddingmate.domain.vendor.entity.VendorStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    List<Vendor> findByStatus(VendorStatus status);

    List<Vendor> findByStatusAndCategoryIdAndRegionContainingIgnoreCaseAndMinPriceGreaterThanEqualAndMaxPriceLessThanEqual(
            VendorStatus status,
            Long categoryId,
            String region,
            long minPrice,
            long maxPrice
    );
}
