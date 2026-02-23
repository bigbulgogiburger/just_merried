package com.weddingmate.api.checklist.dto;

import com.weddingmate.domain.checklist.entity.ChecklistAssignee;
import jakarta.validation.constraints.NotBlank;

public record ChecklistItemCreateRequest(
        @NotBlank String title,
        String description,
        ChecklistAssignee assignee,
        Integer sortOrder
) {
}
