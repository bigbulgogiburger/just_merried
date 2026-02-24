package com.weddingmate.api.market.dto;
import com.weddingmate.domain.market.entity.SecondhandSaleStatus; import jakarta.validation.constraints.NotNull;
public record SecondhandSaleStatusUpdateRequest(@NotNull SecondhandSaleStatus saleStatus){}