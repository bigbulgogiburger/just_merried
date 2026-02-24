package com.weddingmate.domain.market.repository;

import com.weddingmate.domain.market.entity.MarketWishlist;
import com.weddingmate.domain.market.entity.WishlistTargetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarketWishlistRepository extends JpaRepository<MarketWishlist, Long> {
    List<MarketWishlist> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<MarketWishlist> findByUserIdAndTargetTypeAndTargetId(Long userId, WishlistTargetType targetType, Long targetId);
    void deleteByUserIdAndTargetTypeAndTargetId(Long userId, WishlistTargetType targetType, Long targetId);
}
