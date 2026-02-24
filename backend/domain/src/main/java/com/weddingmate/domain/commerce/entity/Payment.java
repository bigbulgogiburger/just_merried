package com.weddingmate.domain.commerce.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import com.weddingmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Table(name="payments") @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) private User user;
    @Column(nullable = false, length = 30) private String orderType;
    private Long orderId;
    @Column(nullable = false) private long amount;
    @Column(nullable = false, length = 30) private String paymentMethod;
    @Column(nullable = false, length = 30) private String provider;
    @Column(length = 120) private String providerTxId;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private PaymentStatus status;
    private LocalDateTime approvedAt; private LocalDateTime canceledAt;
    @Builder public Payment(User user, String orderType, Long orderId, long amount, String paymentMethod, String provider, String providerTxId, PaymentStatus status) { this.user = user; this.orderType = orderType; this.orderId = orderId; this.amount = amount; this.paymentMethod = paymentMethod; this.provider = provider; this.providerTxId = providerTxId; this.status = status == null ? PaymentStatus.PENDING : status; }
    public void approve(String providerTxId){ this.providerTxId = providerTxId; this.status = PaymentStatus.APPROVED; this.approvedAt = LocalDateTime.now(); }
    public void cancel(){ this.status = PaymentStatus.CANCELED; this.canceledAt = LocalDateTime.now(); }
}
