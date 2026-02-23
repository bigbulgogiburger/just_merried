package com.weddingmate.domain.schedule.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import com.weddingmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "schedules")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "all_day", nullable = false)
    private boolean allDay;

    @Column(name = "shared_with_couple", nullable = false)
    private boolean sharedWithCouple;

    @Column(name = "reminder_minutes")
    private Integer reminderMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ScheduleStatus status;

    @Builder
    public Schedule(User user, String title, String description, LocalDateTime startAt,
                    LocalDateTime endAt, boolean allDay, boolean sharedWithCouple, Integer reminderMinutes) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
        this.allDay = allDay;
        this.sharedWithCouple = sharedWithCouple;
        this.reminderMinutes = reminderMinutes;
        this.status = ScheduleStatus.PLANNED;
    }

    public void update(String title, String description, LocalDateTime startAt, LocalDateTime endAt,
                       Boolean allDay, Boolean sharedWithCouple, Integer reminderMinutes, ScheduleStatus status) {
        if (title != null && !title.isBlank()) this.title = title;
        if (description != null) this.description = description;
        if (startAt != null) this.startAt = startAt;
        if (endAt != null) this.endAt = endAt;
        if (allDay != null) this.allDay = allDay;
        if (sharedWithCouple != null) this.sharedWithCouple = sharedWithCouple;
        if (reminderMinutes != null) this.reminderMinutes = reminderMinutes;
        if (status != null) this.status = status;
    }
}
