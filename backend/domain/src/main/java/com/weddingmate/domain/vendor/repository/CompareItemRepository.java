package com.weddingmate.domain.vendor.repository;

import com.weddingmate.domain.vendor.entity.CompareItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompareItemRepository extends JpaRepository<CompareItem, Long> {
    Optional<CompareItem> findByUserIdAndVendorId(Long userId, Long vendorId);
    List<CompareItem> findByUserId(Long userId);
    long countByUserId(Long userId);
}
