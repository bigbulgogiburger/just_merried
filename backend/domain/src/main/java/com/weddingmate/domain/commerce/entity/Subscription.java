package com.weddingmate.domain.commerce.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import com.weddingmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Table(name="subscriptions") @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscription extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id", nullable = false) private User user;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private SubscriptionPlanType planType;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private SubscriptionStatus status;
    private LocalDateTime startedAt; private LocalDateTime endedAt; private LocalDateTime renewAt;
    @Column(nullable = false) private boolean autoRenew;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "last_payment_id") private Payment lastPayment;
    @Builder public Subscription(User user, SubscriptionPlanType planType, SubscriptionStatus status, LocalDateTime startedAt, LocalDateTime endedAt, LocalDateTime renewAt, boolean autoRenew, Payment lastPayment){this.user=user; this.planType=planType==null?SubscriptionPlanType.FREE:planType; this.status=status==null?SubscriptionStatus.ACTIVE:status; this.startedAt=startedAt; this.endedAt=endedAt; this.renewAt=renewAt; this.autoRenew=autoRenew; this.lastPayment=lastPayment;}
    public void activateBasic(Payment payment){ this.planType=SubscriptionPlanType.BASIC; this.status=SubscriptionStatus.ACTIVE; this.startedAt=LocalDateTime.now(); this.renewAt=LocalDateTime.now().plusMonths(1); this.lastPayment=payment; this.autoRenew=true; this.endedAt=null; }
    public void cancel(){ this.status=SubscriptionStatus.CANCELED; this.autoRenew=false; this.endedAt=LocalDateTime.now(); }
}
