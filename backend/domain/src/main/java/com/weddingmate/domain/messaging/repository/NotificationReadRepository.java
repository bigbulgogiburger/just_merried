package com.weddingmate.domain.messaging.repository;

import com.weddingmate.domain.messaging.entity.NotificationRead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationReadRepository extends JpaRepository<NotificationRead, Long> {
    Optional<NotificationRead> findByNotificationIdAndUserId(Long notificationId, Long userId);
    long countByUserId(Long userId);
}
