package com.weddingmate.api.community.dm.dto;

import jakarta.validation.constraints.NotNull;

public record DmRoomCreateRequest(
        @NotNull Long targetUserId
) {
}
