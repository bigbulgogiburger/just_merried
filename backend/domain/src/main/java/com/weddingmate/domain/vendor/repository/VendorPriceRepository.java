package com.weddingmate.domain.vendor.repository;

import com.weddingmate.domain.vendor.entity.VendorPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorPriceRepository extends JpaRepository<VendorPrice, Long> {
    List<VendorPrice> findByVendorIdOrderBySortOrderAsc(Long vendorId);
}
