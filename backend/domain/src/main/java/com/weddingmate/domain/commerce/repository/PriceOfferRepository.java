package com.weddingmate.domain.commerce.repository;

import com.weddingmate.domain.commerce.entity.PriceOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceOfferRepository extends JpaRepository<PriceOffer, Long> {
    List<PriceOffer> findBySecondhandProductIdOrderByCreatedAtDesc(Long secondhandProductId);
    List<PriceOffer> findBySellerUserIdOrBuyerUserIdOrderByCreatedAtDesc(Long sellerUserId, Long buyerUserId);
}
