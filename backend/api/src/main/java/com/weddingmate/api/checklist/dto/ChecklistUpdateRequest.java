package com.weddingmate.api.checklist.dto;

import java.time.LocalDate;

public record ChecklistUpdateRequest(
        String title,
        String description,
        LocalDate startDate,
        LocalDate dueDate
) {
}
