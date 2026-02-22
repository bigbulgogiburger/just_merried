package com.weddingmate.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_settings")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "push_enabled", nullable = false)
    private boolean pushEnabled;

    @Column(name = "marketing_agreed", nullable = false)
    private boolean marketingAgreed;

    @Column(nullable = false, length = 20)
    private String theme;

    @Column(nullable = false, length = 10)
    private String language;

    @Builder
    public UserSettings(User user, boolean pushEnabled, boolean marketingAgreed,
                        String theme, String language) {
        this.user = user;
        this.pushEnabled = pushEnabled;
        this.marketingAgreed = marketingAgreed;
        this.theme = theme != null ? theme : "LIGHT";
        this.language = language != null ? language : "ko";
    }

    public void update(Boolean pushEnabled, Boolean marketingAgreed, String theme, String language) {
        if (pushEnabled != null) this.pushEnabled = pushEnabled;
        if (marketingAgreed != null) this.marketingAgreed = marketingAgreed;
        if (theme != null) this.theme = theme;
        if (language != null) this.language = language;
    }
}
