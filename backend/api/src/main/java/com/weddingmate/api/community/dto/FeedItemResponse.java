package com.weddingmate.api.community.dto;

import com.weddingmate.domain.community.entity.Post;

import java.time.LocalDateTime;

public record FeedItemResponse(
        Long id,
        Long userId,
        String content,
        String region,
        int likeCount,
        int commentCount,
        LocalDateTime createdAt
) {
    public static FeedItemResponse from(Post post) {
        return new FeedItemResponse(
                post.getId(),
                post.getUser().getId(),
                post.getContent(),
                post.getRegion(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getCreatedAt()
        );
    }
}
