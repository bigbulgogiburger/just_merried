package com.weddingmate.api.market.dto;
import com.weddingmate.domain.market.entity.WishlistTargetType; import jakarta.validation.constraints.NotNull;
public record WishlistCreateRequest(@NotNull WishlistTargetType targetType,@NotNull Long targetId){}