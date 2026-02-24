package com.weddingmate.api.community.dto;

import com.weddingmate.domain.community.entity.Follow;

public record FollowUserResponse(
        Long userId,
        String nickname,
        String profileImageUrl
) {
    public static FollowUserResponse fromFollower(Follow follow) {
        return new FollowUserResponse(
                follow.getFollower().getId(),
                follow.getFollower().getNickname(),
                follow.getFollower().getProfileImageUrl()
        );
    }

    public static FollowUserResponse fromFollowing(Follow follow) {
        return new FollowUserResponse(
                follow.getFollowing().getId(),
                follow.getFollowing().getNickname(),
                follow.getFollowing().getProfileImageUrl()
        );
    }
}
