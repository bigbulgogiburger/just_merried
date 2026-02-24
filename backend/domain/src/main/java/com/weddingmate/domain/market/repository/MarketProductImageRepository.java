package com.weddingmate.domain.market.repository;

import com.weddingmate.domain.market.entity.MarketProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketProductImageRepository extends JpaRepository<MarketProductImage, Long> {
    List<MarketProductImage> findByProductIdOrderBySortOrderAsc(Long productId);
}
