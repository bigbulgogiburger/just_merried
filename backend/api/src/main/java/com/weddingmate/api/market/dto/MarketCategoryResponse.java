package com.weddingmate.api.market.dto;
import com.weddingmate.domain.market.entity.MarketCategory;
public record MarketCategoryResponse(Long id,String name,String slug,int sortOrder){ public static MarketCategoryResponse from(MarketCategory c){return new MarketCategoryResponse(c.getId(),c.getName(),c.getSlug(),c.getSortOrder());}}