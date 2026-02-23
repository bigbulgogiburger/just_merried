package com.weddingmate.api.checklist.dto;

import com.weddingmate.domain.checklist.entity.ChecklistItem;

import java.time.LocalDateTime;

public record ChecklistItemResponse(
        Long id,
        String title,
        String description,
        String assignee,
        boolean completed,
        LocalDateTime completedAt,
        int sortOrder
) {
    public static ChecklistItemResponse from(ChecklistItem item) {
        return new ChecklistItemResponse(
                item.getId(),
                item.getTitle(),
                item.getDescription(),
                item.getAssignee().name(),
                item.isCompleted(),
                item.getCompletedAt(),
                item.getSortOrder()
        );
    }
}
