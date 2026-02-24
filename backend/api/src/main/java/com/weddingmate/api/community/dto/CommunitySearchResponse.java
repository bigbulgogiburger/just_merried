package com.weddingmate.api.community.dto;

import com.weddingmate.domain.community.entity.Post;
import com.weddingmate.domain.community.entity.Hashtag;
import com.weddingmate.domain.user.entity.User;

import java.util.List;

public record CommunitySearchResponse(
        List<PostItem> posts,
        List<UserItem> users,
        List<HashtagItem> hashtags
) {
    public record PostItem(Long id, Long userId, String content, String region) {
        public static PostItem from(Post post) {
            return new PostItem(post.getId(), post.getUser().getId(), post.getContent(), post.getRegion());
        }
    }

    public record UserItem(Long id, String nickname, String region) {
        public static UserItem from(User user) {
            return new UserItem(user.getId(), user.getNickname(), user.getRegion());
        }
    }

    public record HashtagItem(Long id, String name, int usageCount) {
        public static HashtagItem from(Hashtag hashtag) {
            return new HashtagItem(hashtag.getId(), hashtag.getName(), hashtag.getUsageCount());
        }
    }
}
