package com.weddingmate.api.checklist.dto;

import com.weddingmate.domain.checklist.entity.ChecklistAssignee;

public record ChecklistItemUpdateRequest(
        String title,
        String description,
        ChecklistAssignee assignee,
        Integer sortOrder
) {
}
