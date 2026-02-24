package com.weddingmate.domain.life.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import com.weddingmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "newhome_checklist_items") @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewHomeChecklistItem extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) private User user;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 30) private NewHomeCategory category;
    @Column(nullable = false, length = 100) private String title;
    @Column(length = 500) private String memo;
    @Column(nullable = false) private boolean completed;
    @Column(length = 30) private String assignee;

    @Builder public NewHomeChecklistItem(User user, NewHomeCategory category, String title, String memo, boolean completed, String assignee){ this.user=user; this.category=category; this.title=title; this.memo=memo; this.completed=completed; this.assignee=assignee; }
    public void update(String title, String memo, Boolean completed, String assignee){ if(title!=null)this.title=title; if(memo!=null)this.memo=memo; if(completed!=null)this.completed=completed; if(assignee!=null)this.assignee=assignee; }
}
