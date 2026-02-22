package com.weddingmate.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "business_profiles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "business_name", nullable = false, length = 100)
    private String businessName;

    @Column(name = "business_number", nullable = false, length = 20)
    private String businessNumber;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(length = 500)
    private String address;

    @Column(length = 20)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BusinessStatus status;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Builder
    public BusinessProfile(User user, String businessName, String businessNumber,
                           String category, String address, String phone, String description) {
        this.user = user;
        this.businessName = businessName;
        this.businessNumber = businessNumber;
        this.category = category;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.status = BusinessStatus.PENDING;
        this.submittedAt = LocalDateTime.now();
    }

    public void approve() {
        this.status = BusinessStatus.APPROVED;
        this.approvedAt = LocalDateTime.now();
    }

    public void reject() {
        this.status = BusinessStatus.REJECTED;
    }
}
