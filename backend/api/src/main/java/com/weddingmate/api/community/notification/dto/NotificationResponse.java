package com.weddingmate.api.community.notification.dto;

import com.weddingmate.domain.messaging.entity.Notification;

import java.time.LocalDateTime;

public record NotificationResponse(
        Long id,
        String type,
        String title,
        String body,
        String targetType,
        Long targetId,
        String payloadJson,
        boolean read,
        LocalDateTime createdAt
) {
    public static NotificationResponse of(Notification notification, boolean read) {
        return new NotificationResponse(
                notification.getId(),
                notification.getType().name(),
                notification.getTitle(),
                notification.getBody(),
                notification.getTargetType(),
                notification.getTargetId(),
                notification.getPayloadJson(),
                read,
                notification.getCreatedAt()
        );
    }
}
