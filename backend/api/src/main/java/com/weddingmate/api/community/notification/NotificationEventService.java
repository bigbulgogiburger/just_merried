package com.weddingmate.api.community.notification;

import com.weddingmate.domain.messaging.entity.Notification;
import com.weddingmate.domain.messaging.entity.NotificationType;
import com.weddingmate.domain.messaging.repository.NotificationRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationEventService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Transactional
    public void onLike(Long actorUserId, Long receiverUserId, Long postId) {
        if (actorUserId.equals(receiverUserId)) return;
        create(receiverUserId, NotificationType.LIKE, "새 좋아요", "내 게시글에 좋아요가 추가되었어요.", "POST", postId,
                "{\"actorUserId\":%d,\"postId\":%d}".formatted(actorUserId, postId));
    }

    @Transactional
    public void onComment(Long actorUserId, Long receiverUserId, Long postId, Long commentId) {
        if (actorUserId.equals(receiverUserId)) return;
        create(receiverUserId, NotificationType.COMMENT, "새 댓글", "내 게시글에 댓글이 등록되었어요.", "POST", postId,
                "{\"actorUserId\":%d,\"postId\":%d,\"commentId\":%d}".formatted(actorUserId, postId, commentId));
    }

    @Transactional
    public void onFollow(Long actorUserId, Long receiverUserId) {
        if (actorUserId.equals(receiverUserId)) return;
        create(receiverUserId, NotificationType.FOLLOW, "새 팔로우", "나를 팔로우한 사용자가 있어요.", "USER", actorUserId,
                "{\"actorUserId\":%d}".formatted(actorUserId));
    }

    @Transactional
    public void onDm(Long actorUserId, Long receiverUserId, Long roomId, Long messageId) {
        if (actorUserId.equals(receiverUserId)) return;
        create(receiverUserId, NotificationType.DM, "새 DM", "새로운 메시지가 도착했어요.", "DM_ROOM", roomId,
                "{\"actorUserId\":%d,\"roomId\":%d,\"messageId\":%d}".formatted(actorUserId, roomId, messageId));
    }

    private void create(Long receiverUserId, NotificationType type, String title, String body,
                        String targetType, Long targetId, String payloadJson) {
        User receiver = userRepository.findById(receiverUserId).orElse(null);
        if (receiver == null) return;

        notificationRepository.save(Notification.builder()
                .user(receiver)
                .type(type)
                .title(title)
                .body(body)
                .targetType(targetType)
                .targetId(targetId)
                .payloadJson(payloadJson)
                .build());
    }
}
