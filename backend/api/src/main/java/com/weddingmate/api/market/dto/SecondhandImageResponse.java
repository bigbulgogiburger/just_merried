package com.weddingmate.api.market.dto;
import com.weddingmate.domain.market.entity.SecondhandProductImage;
public record SecondhandImageResponse(Long id,String imageUrl,int sortOrder){ public static SecondhandImageResponse from(SecondhandProductImage i){return new SecondhandImageResponse(i.getId(),i.getImageUrl(),i.getSortOrder());}}