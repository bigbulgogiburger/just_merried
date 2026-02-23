package com.weddingmate.domain.vendor.repository;

import com.weddingmate.domain.vendor.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserIdAndVendorId(Long userId, Long vendorId);
    List<Favorite> findByUserId(Long userId);
}
