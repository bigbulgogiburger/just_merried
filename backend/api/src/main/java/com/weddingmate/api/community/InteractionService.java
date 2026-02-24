package com.weddingmate.api.community;

import com.weddingmate.api.community.dto.CommentCreateRequest;
import com.weddingmate.api.community.dto.CommentResponse;
import com.weddingmate.common.exception.BusinessException;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.api.community.notification.NotificationEventService;
import com.weddingmate.domain.community.entity.Comment;
import com.weddingmate.domain.community.entity.Post;
import com.weddingmate.domain.community.entity.PostLike;
import com.weddingmate.domain.community.repository.CommentRepository;
import com.weddingmate.domain.community.repository.PostLikeRepository;
import com.weddingmate.domain.community.repository.PostRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InteractionService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NotificationEventService notificationEventService;

    @Transactional
    public void like(Long userId, Long postId) {
        Post post = getPost(postId);
        if (postLikeRepository.findByPostIdAndUserId(postId, userId).isPresent()) return;

        postLikeRepository.save(PostLike.builder().post(post).user(getUser(userId)).build());
        post.increaseLikeCount();
        notificationEventService.onLike(userId, post.getUser().getId(), postId);
    }

    @Transactional
    public void unlike(Long userId, Long postId) {
        Post post = getPost(postId);
        postLikeRepository.findByPostIdAndUserId(postId, userId).ifPresent(like -> {
            postLikeRepository.delete(like);
            post.decreaseLikeCount();
        });
    }

    @Transactional
    public CommentResponse createComment(Long userId, Long postId, CommentCreateRequest request) {
        Post post = getPost(postId);
        User user = getUser(userId);
        Comment parent = null;

        if (request.parentId() != null) {
            parent = commentRepository.findById(request.parentId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND, "부모 댓글을 찾을 수 없습니다."));
            if (!parent.getPost().getId().equals(postId)) {
                throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "부모 댓글의 게시글이 일치하지 않습니다.");
            }
        }

        Comment comment = commentRepository.save(Comment.builder()
                .post(post)
                .user(user)
                .parent(parent)
                .content(request.content())
                .build());
        post.increaseCommentCount();
        notificationEventService.onComment(userId, post.getUser().getId(), postId, comment.getId());

        return CommentResponse.from(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new NotFoundException(ErrorCode.POST_NOT_FOUND, "게시글을 찾을 수 없습니다.");
        }
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId)
                .stream().map(CommentResponse::from).toList();
    }

    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMENT_NOT_FOUND, "댓글을 찾을 수 없습니다."));

        if (!comment.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.POST_ACCESS_DENIED, "본인 댓글만 삭제할 수 있습니다.");
        }

        Post post = comment.getPost();
        commentRepository.delete(comment);
        post.decreaseCommentCount();
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND, "게시글을 찾을 수 없습니다."));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }
}
