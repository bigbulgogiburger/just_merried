package com.weddingmate.api.market.dto;
import com.weddingmate.domain.market.entity.MarketProduct;
import java.time.LocalDateTime;
public record MarketProductSummaryResponse(Long id,String name,long basePrice,String status,int stockQuantity,String thumbnailUrl,LocalDateTime createdAt){ public static MarketProductSummaryResponse of(MarketProduct p,String t){return new MarketProductSummaryResponse(p.getId(),p.getName(),p.getBasePrice(),p.getStatus().name(),p.getStockQuantity(),t,p.getCreatedAt());}}