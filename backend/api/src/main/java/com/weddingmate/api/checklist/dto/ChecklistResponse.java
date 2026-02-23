package com.weddingmate.api.checklist.dto;

import com.weddingmate.domain.checklist.entity.Checklist;

import java.time.LocalDate;
import java.util.List;

public record ChecklistResponse(
        Long id,
        String title,
        String description,
        LocalDate startDate,
        LocalDate dueDate,
        String status,
        List<ChecklistItemResponse> items,
        int progressPercent
) {
    public static ChecklistResponse of(Checklist checklist, List<ChecklistItemResponse> items, int progressPercent) {
        return new ChecklistResponse(
                checklist.getId(),
                checklist.getTitle(),
                checklist.getDescription(),
                checklist.getStartDate(),
                checklist.getDueDate(),
                checklist.getStatus().name(),
                items,
                progressPercent
        );
    }
}
