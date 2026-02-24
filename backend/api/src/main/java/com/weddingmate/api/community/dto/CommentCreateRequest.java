package com.weddingmate.api.community.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentCreateRequest(
        Long parentId,
        @NotBlank String content
) {
}
