package com.weddingmate.domain.market.repository;

import com.weddingmate.domain.market.entity.SecondhandProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecondhandProductImageRepository extends JpaRepository<SecondhandProductImage, Long> {
    List<SecondhandProductImage> findBySecondhandProductIdOrderBySortOrderAsc(Long secondhandProductId);
}
