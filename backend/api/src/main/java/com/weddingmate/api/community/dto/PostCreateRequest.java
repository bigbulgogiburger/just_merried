package com.weddingmate.api.community.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PostCreateRequest(
        @NotBlank String content,
        @NotBlank String region,
        List<PostMediaRequest> media,
        List<String> hashtags
) {
    public record PostMediaRequest(
            @NotBlank String mediaUrl,
            @NotBlank String mediaType,
            Integer sortOrder
    ) {
    }
}
