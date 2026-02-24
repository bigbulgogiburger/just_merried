package com.weddingmate.api.community.notification;

import com.weddingmate.api.community.notification.dto.NotificationResponse;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.messaging.entity.Notification;
import com.weddingmate.domain.messaging.entity.NotificationRead;
import com.weddingmate.domain.messaging.repository.NotificationReadRepository;
import com.weddingmate.domain.messaging.repository.NotificationRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationReadRepository notificationReadRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<NotificationResponse> list(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(notification -> NotificationResponse.of(
                        notification,
                        notificationReadRepository.findByNotificationIdAndUserId(notification.getId(), userId).isPresent()
                ))
                .toList();
    }

    @Transactional
    public void markRead(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND, "알림을 찾을 수 없습니다."));

        if (!notification.getUser().getId().equals(userId)) {
            throw new NotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND, "알림을 찾을 수 없습니다.");
        }

        if (notificationReadRepository.findByNotificationIdAndUserId(notificationId, userId).isEmpty()) {
            notificationReadRepository.save(NotificationRead.builder()
                    .notification(notification)
                    .user(getUser(userId))
                    .build());
        }
    }

    @Transactional
    public void markAllRead(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        User user = getUser(userId);

        for (Notification notification : notifications) {
            if (notificationReadRepository.findByNotificationIdAndUserId(notification.getId(), userId).isEmpty()) {
                notificationReadRepository.save(NotificationRead.builder()
                        .notification(notification)
                        .user(user)
                        .build());
            }
        }
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }
}
