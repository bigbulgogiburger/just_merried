package com.weddingmate.domain.life.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import com.weddingmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity @Table(name = "family_events") @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FamilyEvent extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) private User user;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "family_member_id") private FamilyMember familyMember;
    @Column(nullable = false, length = 100) private String title;
    @Column(nullable = false) private LocalDate eventDate;
    @Column(nullable = false) private boolean lunar;
    @Column(nullable = false) private int remindDaysBefore;
    @Column(nullable = false) private boolean repeatYearly;

    @Builder public FamilyEvent(User user, FamilyMember familyMember, String title, LocalDate eventDate, boolean lunar, int remindDaysBefore, boolean repeatYearly){ this.user=user; this.familyMember=familyMember; this.title=title; this.eventDate=eventDate; this.lunar=lunar; this.remindDaysBefore=remindDaysBefore; this.repeatYearly=repeatYearly; }
    public void update(FamilyMember member, String title, LocalDate eventDate, Boolean lunar, Integer remindDaysBefore, Boolean repeatYearly){ if(member!=null)this.familyMember=member; if(title!=null)this.title=title; if(eventDate!=null)this.eventDate=eventDate; if(lunar!=null)this.lunar=lunar; if(remindDaysBefore!=null)this.remindDaysBefore=remindDaysBefore; if(repeatYearly!=null)this.repeatYearly=repeatYearly; }
}
