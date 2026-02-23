package com.weddingmate.api.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ScheduleCreateRequest(
        @NotBlank String title,
        String description,
        @NotNull LocalDateTime startAt,
        @NotNull LocalDateTime endAt,
        Boolean allDay,
        Boolean sharedWithCouple,
        Integer reminderMinutes
) {
}
