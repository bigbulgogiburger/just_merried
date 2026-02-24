package com.weddingmate.api.life.dto;

import com.weddingmate.domain.life.entity.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class LifeDtos {
    public record FamilyMemberRequest(@NotBlank String name, @NotBlank String relation, @NotNull LocalDate birthDate, @NotNull Boolean lunar) {}
    public record FamilyMemberResponse(Long id, String name, String relation, LocalDate birthDate, boolean lunar){ public static FamilyMemberResponse from(FamilyMember m){ return new FamilyMemberResponse(m.getId(), m.getName(), m.getRelation(), m.getBirthDate(), m.isLunar()); }}

    public record FamilyEventRequest(@NotBlank String title, @NotNull LocalDate eventDate, @NotNull Boolean lunar, Long familyMemberId, @Min(0) @Max(30) Integer remindDaysBefore, Boolean repeatYearly) {}
    public record FamilyEventResponse(Long id, String title, LocalDate eventDate, boolean lunar, Long familyMemberId, Integer remindDaysBefore, boolean repeatYearly){ public static FamilyEventResponse from(FamilyEvent e){ return new FamilyEventResponse(e.getId(), e.getTitle(), e.getEventDate(), e.isLunar(), e.getFamilyMember()==null?null:e.getFamilyMember().getId(), e.getRemindDaysBefore(), e.isRepeatYearly()); }}

    public record NewHomeItemRequest(@NotNull NewHomeCategory category, @NotBlank String title, String memo, Boolean completed, String assignee) {}
    public record NewHomeItemResponse(Long id, NewHomeCategory category, String title, String memo, boolean completed, String assignee){ public static NewHomeItemResponse from(NewHomeChecklistItem i){ return new NewHomeItemResponse(i.getId(), i.getCategory(), i.getTitle(), i.getMemo(), i.isCompleted(), i.getAssignee()); }}

    public record LifeDashboardResponse(long dDay, int upcomingEventCount, int newHomeTotal, int newHomeCompleted) {}
}
