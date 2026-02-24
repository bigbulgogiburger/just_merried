package com.weddingmate.api.market.dto;
import com.weddingmate.domain.market.entity.SecondhandProduct;
import java.time.LocalDateTime;
public record SecondhandSummaryResponse(Long id,String title,long price,String region,String saleStatus,String conditionStatus,String thumbnailUrl,LocalDateTime createdAt){ public static SecondhandSummaryResponse of(SecondhandProduct p,String t){return new SecondhandSummaryResponse(p.getId(),p.getTitle(),p.getPrice(),p.getRegion(),p.getSaleStatus().name(),p.getConditionStatus().name(),t,p.getCreatedAt());}}