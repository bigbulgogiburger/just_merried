package com.weddingmate.domain.life.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import com.weddingmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity @Table(name = "family_members") @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FamilyMember extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) private User user;
    @Column(nullable = false, length = 50) private String name;
    @Column(nullable = false, length = 30) private String relation;
    @Column(nullable = false) private LocalDate birthDate;
    @Column(nullable = false) private boolean lunar;

    @Builder public FamilyMember(User user, String name, String relation, LocalDate birthDate, boolean lunar){ this.user=user; this.name=name; this.relation=relation; this.birthDate=birthDate; this.lunar=lunar; }
    public void update(String name, String relation, LocalDate birthDate, Boolean lunar){ if(name!=null)this.name=name; if(relation!=null)this.relation=relation; if(birthDate!=null)this.birthDate=birthDate; if(lunar!=null)this.lunar=lunar; }
}
