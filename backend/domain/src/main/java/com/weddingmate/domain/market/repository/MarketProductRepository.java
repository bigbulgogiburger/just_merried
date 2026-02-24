package com.weddingmate.domain.market.repository;

import com.weddingmate.domain.market.entity.MarketProduct;
import com.weddingmate.domain.market.entity.MarketProductStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarketProductRepository extends JpaRepository<MarketProduct, Long> {
    List<MarketProduct> findByCategoryIdAndStatusOrderByCreatedAtDesc(Long categoryId, MarketProductStatus status, Pageable pageable);
    List<MarketProduct> findByStatusOrderByCreatedAtDesc(MarketProductStatus status, Pageable pageable);

    @Query("select p from MarketProduct p where p.status = :status and lower(p.name) like lower(concat('%', :keyword, '%')) order by p.createdAt desc")
    List<MarketProduct> searchByKeyword(@Param("keyword") String keyword, @Param("status") MarketProductStatus status, Pageable pageable);
}
