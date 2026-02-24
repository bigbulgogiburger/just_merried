package com.weddingmate.domain.commerce.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import com.weddingmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="trade_reviews") @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TradeReview extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "escrow_transaction_id", nullable = false) private EscrowTransaction escrowTransaction;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "reviewer_user_id", nullable = false) private User reviewerUser;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "reviewee_user_id", nullable = false) private User revieweeUser;
    @Column(nullable = false) private int scoreKindness;
    @Column(nullable = false) private int scorePunctuality;
    @Column(nullable = false) private int scoreQuality;
    @Column(columnDefinition = "TEXT") private String content;
    @Builder public TradeReview(EscrowTransaction escrowTransaction, User reviewerUser, User revieweeUser, int scoreKindness, int scorePunctuality, int scoreQuality, String content){this.escrowTransaction=escrowTransaction; this.reviewerUser=reviewerUser; this.revieweeUser=revieweeUser; this.scoreKindness=scoreKindness; this.scorePunctuality=scorePunctuality; this.scoreQuality=scoreQuality; this.content=content;}
    public double averageScore(){ return (scoreKindness + scorePunctuality + scoreQuality) / 3.0; }
}
