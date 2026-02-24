package com.weddingmate.api.community;

import com.weddingmate.api.community.dto.PostCreateRequest;
import com.weddingmate.api.community.dto.PostDetailResponse;
import com.weddingmate.common.exception.BusinessException;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.community.entity.Hashtag;
import com.weddingmate.domain.community.entity.Post;
import com.weddingmate.domain.community.entity.PostHashtag;
import com.weddingmate.domain.community.entity.PostMedia;
import com.weddingmate.domain.community.repository.HashtagRepository;
import com.weddingmate.domain.community.repository.PostHashtagRepository;
import com.weddingmate.domain.community.repository.PostMediaRepository;
import com.weddingmate.domain.community.repository.PostRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.entity.UserRole;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMediaRepository postMediaRepository;
    private final PostHashtagRepository postHashtagRepository;
    private final HashtagRepository hashtagRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostDetailResponse create(Long userId, PostCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));

        if (user.getRole() != UserRole.USER) {
            throw new BusinessException(ErrorCode.POST_ACCESS_DENIED, "일반 회원만 게시글을 작성할 수 있습니다.");
        }

        Post post = postRepository.save(Post.builder()
                .user(user)
                .content(request.content())
                .region(request.region())
                .build());

        if (request.media() != null) {
            request.media().forEach(media -> postMediaRepository.save(PostMedia.builder()
                    .post(post)
                    .mediaUrl(media.mediaUrl())
                    .mediaType(media.mediaType())
                    .sortOrder(media.sortOrder() != null ? media.sortOrder() : 0)
                    .build()));
        }

        if (request.hashtags() != null) {
            request.hashtags().stream()
                    .filter(tag -> tag != null && !tag.isBlank())
                    .map(this::normalizeHashtag)
                    .distinct()
                    .forEach(tag -> {
                        Hashtag hashtag = hashtagRepository.findByName(tag)
                                .orElseGet(() -> hashtagRepository.save(Hashtag.builder().name(tag).build()));
                        hashtag.increaseUsage();
                        postHashtagRepository.save(PostHashtag.builder().post(post).hashtag(hashtag).build());
                    });
        }

        return detail(post.getId());
    }

    @Transactional(readOnly = true)
    public PostDetailResponse detail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND, "게시글을 찾을 수 없습니다."));

        List<PostDetailResponse.PostMediaResponse> media = postMediaRepository.findByPostIdOrderBySortOrderAsc(postId)
                .stream()
                .map(PostDetailResponse.PostMediaResponse::from)
                .toList();

        List<String> hashtags = postHashtagRepository.findByPostId(postId)
                .stream()
                .map(ph -> ph.getHashtag().getName())
                .toList();

        return PostDetailResponse.of(post, media, hashtags);
    }

    @Transactional
    public void delete(Long userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND, "게시글을 찾을 수 없습니다."));

        if (!post.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.POST_ACCESS_DENIED, "본인 게시글만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    private String normalizeHashtag(String tag) {
        String normalized = tag.trim().toLowerCase();
        return normalized.startsWith("#") ? normalized.substring(1) : normalized;
    }
}
