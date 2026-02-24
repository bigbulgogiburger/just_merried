package com.weddingmate.api.community.dm.dto;

import jakarta.validation.constraints.NotBlank;

public record DmMessageCreateRequest(
        @NotBlank String content
) {
}
