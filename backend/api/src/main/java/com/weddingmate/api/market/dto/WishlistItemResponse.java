package com.weddingmate.api.market.dto;
import com.weddingmate.domain.market.entity.MarketWishlist; import java.time.LocalDateTime;
public record WishlistItemResponse(Long id,String targetType,Long targetId,LocalDateTime createdAt){ public static WishlistItemResponse from(MarketWishlist w){return new WishlistItemResponse(w.getId(),w.getTargetType().name(),w.getTargetId(),w.getCreatedAt());}}