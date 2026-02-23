package com.weddingmate.api.schedule.dto;

import com.weddingmate.domain.schedule.entity.Schedule;

import java.time.LocalDateTime;

public record ScheduleResponse(
        Long id,
        String title,
        String description,
        LocalDateTime startAt,
        LocalDateTime endAt,
        boolean allDay,
        boolean sharedWithCouple,
        Integer reminderMinutes,
        String status
) {
    public static ScheduleResponse from(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getDescription(),
                schedule.getStartAt(),
                schedule.getEndAt(),
                schedule.isAllDay(),
                schedule.isSharedWithCouple(),
                schedule.getReminderMinutes(),
                schedule.getStatus().name()
        );
    }
}
