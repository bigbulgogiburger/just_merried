package com.weddingmate.domain.couple.entity;

import com.weddingmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "couple_members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoupleMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id", nullable = false)
    private Couple couple;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_in_couple", nullable = false, length = 20)
    private CoupleRole roleInCouple;

    @Column(name = "joined_at", nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    @Builder
    public CoupleMember(Couple couple, User user, CoupleRole roleInCouple) {
        this.couple = couple;
        this.user = user;
        this.roleInCouple = roleInCouple;
        this.joinedAt = LocalDateTime.now();
    }
}
