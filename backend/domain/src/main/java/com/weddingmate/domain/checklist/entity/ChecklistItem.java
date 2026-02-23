package com.weddingmate.domain.checklist.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "checklist_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChecklistItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id", nullable = false)
    private Checklist checklist;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ChecklistAssignee assignee;

    @Column(nullable = false)
    private boolean completed;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Builder
    public ChecklistItem(Checklist checklist, String title, String description, ChecklistAssignee assignee, int sortOrder) {
        this.checklist = checklist;
        this.title = title;
        this.description = description;
        this.assignee = assignee != null ? assignee : ChecklistAssignee.BOTH;
        this.sortOrder = sortOrder;
        this.completed = false;
    }
}
