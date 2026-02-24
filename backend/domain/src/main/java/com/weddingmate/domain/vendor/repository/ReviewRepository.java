package com.weddingmate.domain.vendor.repository;

import com.weddingmate.domain.vendor.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByVendorIdOrderByCreatedAtDesc(Long vendorId);
    long countByVendorId(Long vendorId);

    @Query("select coalesce(sum(r.rating), 0) from Review r where r.vendor.id = :vendorId")
    long sumRatingByVendorId(Long vendorId);
}
