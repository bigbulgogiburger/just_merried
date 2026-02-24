package com.weddingmate.domain.messaging.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import com.weddingmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private NotificationType type;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(name = "target_type", length = 30)
    private String targetType;

    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "payload_json", columnDefinition = "TEXT")
    private String payloadJson;

    @Builder
    public Notification(User user, NotificationType type, String title, String body,
                        String targetType, Long targetId, String payloadJson) {
        this.user = user;
        this.type = type;
        this.title = title;
        this.body = body;
        this.targetType = targetType;
        this.targetId = targetId;
        this.payloadJson = payloadJson;
    }
}
