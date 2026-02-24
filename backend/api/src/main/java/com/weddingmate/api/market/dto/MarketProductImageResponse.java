package com.weddingmate.api.market.dto;
import com.weddingmate.domain.market.entity.MarketProductImage;
public record MarketProductImageResponse(Long id,String imageUrl,int sortOrder){ public static MarketProductImageResponse from(MarketProductImage i){return new MarketProductImageResponse(i.getId(),i.getImageUrl(),i.getSortOrder());}}