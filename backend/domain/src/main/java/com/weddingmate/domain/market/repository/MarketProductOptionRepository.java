package com.weddingmate.domain.market.repository;

import com.weddingmate.domain.market.entity.MarketProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketProductOptionRepository extends JpaRepository<MarketProductOption, Long> {
    List<MarketProductOption> findByProductIdOrderByCreatedAtAsc(Long productId);
}
