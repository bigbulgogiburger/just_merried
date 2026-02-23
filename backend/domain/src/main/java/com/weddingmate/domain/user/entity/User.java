package com.weddingmate.domain.user.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "wedding_status", nullable = false, length = 20)
    private WeddingStatus weddingStatus;

    @Column(name = "wedding_date")
    private LocalDate weddingDate;

    @Column(length = 50)
    private String region;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserGrade grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OAuthProvider provider;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Builder
    public User(String email, String nickname, String profileImageUrl,
                WeddingStatus weddingStatus, LocalDate weddingDate, String region,
                UserRole role, UserGrade grade, OAuthProvider provider, String providerId) {
        this.email = email;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.weddingStatus = weddingStatus != null ? weddingStatus : WeddingStatus.PREPARING;
        this.weddingDate = weddingDate;
        this.region = region;
        this.role = role != null ? role : UserRole.USER;
        this.grade = grade != null ? grade : UserGrade.FREE;
        this.provider = provider;
        this.providerId = providerId;
    }

    public void updateProfile(String nickname, String profileImageUrl, String region) {
        if (nickname != null) this.nickname = nickname;
        if (profileImageUrl != null) this.profileImageUrl = profileImageUrl;
        if (region != null) this.region = region;
    }

    public void updateWeddingInfo(WeddingStatus weddingStatus, LocalDate weddingDate) {
        this.weddingStatus = weddingStatus;
        this.weddingDate = weddingDate;
    }

    public void promoteToVendor() {
        this.role = UserRole.VENDOR;
    }
}
