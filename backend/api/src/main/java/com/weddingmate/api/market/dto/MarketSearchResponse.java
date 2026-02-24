package com.weddingmate.api.market.dto;
import java.util.List;
public record MarketSearchResponse(List<MarketProductSummaryResponse> latestMarketProducts,List<SecondhandSummaryResponse> latestSecondhandProducts,List<MarketProductSummaryResponse> popularMarketProducts,List<SecondhandSummaryResponse> popularSecondhandProducts){}