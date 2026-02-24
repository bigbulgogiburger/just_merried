package com.weddingmate.domain.commerce.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import com.weddingmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="settlements") @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Settlement extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "seller_user_id", nullable = false) private User sellerUser;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 30) private SettlementType settlementType;
    private Long referenceId;
    @Column(nullable = false) private long grossAmount;
    @Column(nullable = false, precision = 5, scale = 2) private BigDecimal feeRate;
    @Column(nullable = false) private long feeAmount;
    @Column(nullable = false) private long netAmount;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private SettlementStatus status;
    private LocalDateTime settledAt;
    @Builder public Settlement(User sellerUser, SettlementType settlementType, Long referenceId, long grossAmount, BigDecimal feeRate, long feeAmount, long netAmount, SettlementStatus status) { this.sellerUser=sellerUser; this.settlementType=settlementType; this.referenceId=referenceId; this.grossAmount=grossAmount; this.feeRate=feeRate; this.feeAmount=feeAmount; this.netAmount=netAmount; this.status=status==null?SettlementStatus.PENDING:status; }
    public void settle(){ this.status=SettlementStatus.SETTLED; this.settledAt= LocalDateTime.now(); }
}
