package com.weddingmate.domain.vendor.repository;

import com.weddingmate.domain.vendor.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByVendorIdOrderByCreatedAtDesc(Long vendorId);
    long countByVendorId(Long vendorId);
}
