package com.weddingmate.api.schedule.dto;

import com.weddingmate.domain.schedule.entity.ScheduleStatus;

import java.time.LocalDateTime;

public record ScheduleUpdateRequest(
        String title,
        String description,
        LocalDateTime startAt,
        LocalDateTime endAt,
        Boolean allDay,
        Boolean sharedWithCouple,
        Integer reminderMinutes,
        ScheduleStatus status
) {
}
