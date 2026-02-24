package com.weddingmate.api.community;

import com.weddingmate.api.community.dto.CommunitySearchResponse;
import com.weddingmate.api.community.dto.HashtagResponse;
import com.weddingmate.domain.community.repository.HashtagRepository;
import com.weddingmate.domain.community.repository.PostRepository;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscoveryService {

    private final HashtagRepository hashtagRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<HashtagResponse> trendingHashtags(int size) {
        return hashtagRepository.findAllByOrderByUsageCountDesc(PageRequest.of(0, size)).stream()
                .map(HashtagResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public CommunitySearchResponse search(String query, int size) {
        String keyword = query == null ? "" : query.trim();
        if (keyword.isBlank()) {
            return new CommunitySearchResponse(List.of(), List.of(), List.of());
        }

        var pageable = PageRequest.of(0, size);

        var posts = postRepository.findByContentContainingIgnoreCaseOrderByCreatedAtDesc(keyword, pageable)
                .stream()
                .map(CommunitySearchResponse.PostItem::from)
                .toList();

        var users = userRepository.findByNicknameContainingIgnoreCase(keyword, pageable)
                .stream()
                .map(CommunitySearchResponse.UserItem::from)
                .toList();

        var hashtags = hashtagRepository.findByNameContainingIgnoreCaseOrderByUsageCountDesc(keyword.toLowerCase(), pageable)
                .stream()
                .map(CommunitySearchResponse.HashtagItem::from)
                .toList();

        return new CommunitySearchResponse(posts, users, hashtags);
    }
}
