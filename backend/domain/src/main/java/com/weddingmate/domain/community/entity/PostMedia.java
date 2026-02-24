package com.weddingmate.domain.community.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_media")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostMedia extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "media_url", nullable = false, length = 500)
    private String mediaUrl;

    @Column(name = "media_type", nullable = false, length = 20)
    private String mediaType;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Builder
    public PostMedia(Post post, String mediaUrl, String mediaType, int sortOrder) {
        this.post = post;
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
        this.sortOrder = sortOrder;
    }
}
