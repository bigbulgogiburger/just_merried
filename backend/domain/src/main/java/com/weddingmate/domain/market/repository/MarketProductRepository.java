package com.weddingmate.domain.market.repository;

import com.weddingmate.domain.market.entity.MarketProduct;
import com.weddingmate.domain.market.entity.MarketProductStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketProductRepository extends JpaRepository<MarketProduct, Long> {
    List<MarketProduct> findByCategoryIdAndStatusOrderByCreatedAtDesc(Long categoryId, MarketProductStatus status, Pageable pageable);
    List<MarketProduct> findByStatusOrderByCreatedAtDesc(MarketProductStatus status, Pageable pageable);
}
