package com.weddingmate.api.community.dto;

import com.weddingmate.domain.community.entity.Post;
import com.weddingmate.domain.community.entity.PostMedia;

import java.time.LocalDateTime;
import java.util.List;

public record PostDetailResponse(
        Long id,
        Long userId,
        String content,
        String region,
        int likeCount,
        int commentCount,
        LocalDateTime createdAt,
        List<PostMediaResponse> media,
        List<String> hashtags
) {
    public record PostMediaResponse(Long id, String mediaUrl, String mediaType, int sortOrder) {
        public static PostMediaResponse from(PostMedia media) {
            return new PostMediaResponse(media.getId(), media.getMediaUrl(), media.getMediaType(), media.getSortOrder());
        }
    }

    public static PostDetailResponse of(Post post, List<PostMediaResponse> media, List<String> hashtags) {
        return new PostDetailResponse(
                post.getId(),
                post.getUser().getId(),
                post.getContent(),
                post.getRegion(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getCreatedAt(),
                media,
                hashtags
        );
    }
}
