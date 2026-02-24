package com.weddingmate.domain.community.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hashtags")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "usage_count", nullable = false)
    private int usageCount;

    @Builder
    public Hashtag(String name) {
        this.name = name;
        this.usageCount = 0;
    }

    public void increaseUsage() {
        this.usageCount += 1;
    }
}
