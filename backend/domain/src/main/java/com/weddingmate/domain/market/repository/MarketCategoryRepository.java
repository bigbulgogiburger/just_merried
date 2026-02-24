package com.weddingmate.domain.market.repository;

import com.weddingmate.domain.market.entity.MarketCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketCategoryRepository extends JpaRepository<MarketCategory, Long> {
    Optional<MarketCategory> findBySlug(String slug);
    java.util.List<MarketCategory> findAllByOrderBySortOrderAsc();
}
