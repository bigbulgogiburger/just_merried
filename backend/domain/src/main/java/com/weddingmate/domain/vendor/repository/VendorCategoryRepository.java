package com.weddingmate.domain.vendor.repository;

import com.weddingmate.domain.vendor.entity.VendorCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorCategoryRepository extends JpaRepository<VendorCategory, Long> {
    Optional<VendorCategory> findByName(String name);
}
