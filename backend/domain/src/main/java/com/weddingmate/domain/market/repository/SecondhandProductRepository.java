package com.weddingmate.domain.market.repository;

import com.weddingmate.domain.market.entity.SecondhandProduct;
import com.weddingmate.domain.market.entity.SecondhandSaleStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SecondhandProductRepository extends JpaRepository<SecondhandProduct, Long> {
    List<SecondhandProduct> findBySaleStatusOrderByCreatedAtDesc(SecondhandSaleStatus saleStatus, Pageable pageable);
    List<SecondhandProduct> findByRegionAndSaleStatusOrderByCreatedAtDesc(String region, SecondhandSaleStatus saleStatus, Pageable pageable);
    List<SecondhandProduct> findBySellerUserIdOrderByCreatedAtDesc(Long sellerUserId);

    @Query("select s from SecondhandProduct s where s.saleStatus = :saleStatus and lower(s.title) like lower(concat('%', :keyword, '%')) order by s.createdAt desc")
    List<SecondhandProduct> searchByKeyword(@Param("keyword") String keyword, @Param("saleStatus") SecondhandSaleStatus saleStatus, Pageable pageable);
}
