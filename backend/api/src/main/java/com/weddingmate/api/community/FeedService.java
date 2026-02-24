package com.weddingmate.api.community;

import com.weddingmate.api.community.dto.FeedItemResponse;
import com.weddingmate.domain.community.repository.FollowRepository;
import com.weddingmate.domain.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final PostRepository postRepository;
    private final FollowRepository followRepository;

    @Transactional(readOnly = true)
    public List<FeedItemResponse> getGlobalFeed(int page, int size) {
        return postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size))
                .stream().map(FeedItemResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<FeedItemResponse> getFollowingFeed(Long userId, int page, int size) {
        List<Long> followingIds = followRepository.findByFollowerId(userId).stream()
                .map(f -> f.getFollowing().getId())
                .toList();

        if (followingIds.isEmpty()) {
            return List.of();
        }

        return postRepository.findByUserIdInOrderByCreatedAtDesc(followingIds, PageRequest.of(page, size))
                .stream().map(FeedItemResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<FeedItemResponse> getRegionFeed(String region, int page, int size) {
        return postRepository.findByRegionOrderByCreatedAtDesc(region, PageRequest.of(page, size))
                .stream().map(FeedItemResponse::from).toList();
    }
}
