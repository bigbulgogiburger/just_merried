package com.weddingmate.api.community.dto;

import com.weddingmate.domain.community.entity.Hashtag;

public record HashtagResponse(
        Long id,
        String name,
        int usageCount
) {
    public static HashtagResponse from(Hashtag hashtag) {
        return new HashtagResponse(hashtag.getId(), hashtag.getName(), hashtag.getUsageCount());
    }
}
