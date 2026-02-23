package com.weddingmate.api.dashboard.dto;

import java.time.LocalDate;

public record DashboardSummaryResponse(
        LocalDate weddingDate,
        long dDay,
        int progressPercent,
        int todayTodoCount,
        int completedTodoCount
) {
}
