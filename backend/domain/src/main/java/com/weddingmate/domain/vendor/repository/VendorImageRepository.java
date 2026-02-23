package com.weddingmate.domain.vendor.repository;

import com.weddingmate.domain.vendor.entity.VendorImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorImageRepository extends JpaRepository<VendorImage, Long> {
    List<VendorImage> findByVendorIdOrderBySortOrderAsc(Long vendorId);
}
