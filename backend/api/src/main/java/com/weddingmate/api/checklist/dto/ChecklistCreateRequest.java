package com.weddingmate.api.checklist.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ChecklistCreateRequest(
        @NotBlank String title,
        String description,
        LocalDate startDate,
        LocalDate dueDate
) {
}
