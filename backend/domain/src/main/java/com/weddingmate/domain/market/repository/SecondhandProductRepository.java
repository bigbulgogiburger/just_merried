package com.weddingmate.domain.market.repository;

import com.weddingmate.domain.market.entity.SecondhandProduct;
import com.weddingmate.domain.market.entity.SecondhandSaleStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecondhandProductRepository extends JpaRepository<SecondhandProduct, Long> {
    List<SecondhandProduct> findBySaleStatusOrderByCreatedAtDesc(SecondhandSaleStatus saleStatus, Pageable pageable);
    List<SecondhandProduct> findByRegionAndSaleStatusOrderByCreatedAtDesc(String region, SecondhandSaleStatus saleStatus, Pageable pageable);
    List<SecondhandProduct> findBySellerUserIdOrderByCreatedAtDesc(Long sellerUserId);
}
