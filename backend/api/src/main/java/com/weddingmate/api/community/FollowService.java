package com.weddingmate.api.community;

import com.weddingmate.api.community.dto.FollowUserResponse;
import com.weddingmate.common.exception.BusinessException;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.community.entity.Follow;
import com.weddingmate.domain.community.repository.FollowRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new BusinessException(ErrorCode.FOLLOW_SELF_NOT_ALLOWED, "자기 자신을 팔로우할 수 없습니다.");
        }

        if (followRepository.findByFollowerIdAndFollowingId(followerId, followingId).isPresent()) {
            return;
        }

        User follower = getUser(followerId);
        User following = getUser(followingId);

        followRepository.save(Follow.builder()
                .follower(follower)
                .following(following)
                .build());
    }

    @Transactional
    public void unfollow(Long followerId, Long followingId) {
        Follow follow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FOLLOW_NOT_FOUND, "팔로우 관계를 찾을 수 없습니다."));
        followRepository.delete(follow);
    }

    @Transactional(readOnly = true)
    public List<FollowUserResponse> followers(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다.");
        }
        return followRepository.findByFollowingId(userId)
                .stream()
                .map(FollowUserResponse::fromFollower)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FollowUserResponse> following(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다.");
        }
        return followRepository.findByFollowerId(userId)
                .stream()
                .map(FollowUserResponse::fromFollowing)
                .toList();
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }
}
