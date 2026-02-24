package com.weddingmate.api.market.dto;
import com.weddingmate.domain.market.entity.MarketProductOption;
public record MarketProductOptionResponse(Long id,String optionName,long extraPrice,int stockQuantity){ public static MarketProductOptionResponse from(MarketProductOption o){return new MarketProductOptionResponse(o.getId(),o.getOptionName(),o.getExtraPrice(),o.getStockQuantity());}}