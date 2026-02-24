package com.weddingmate.domain.commerce.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import com.weddingmate.domain.market.entity.SecondhandProduct;
import com.weddingmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Table(name="escrow_transactions") @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EscrowTransaction extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "secondhand_product_id", nullable = false) private SecondhandProduct secondhandProduct;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "buyer_user_id", nullable = false) private User buyerUser;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "seller_user_id", nullable = false) private User sellerUser;
    @Column(nullable = false) private long amount;
    @Column(nullable = false) private long feeAmount;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 30) private EscrowStatus status;
    @Column(length = 80) private String trackingNumber;
    @Column(length = 40) private String carrier;
    private LocalDateTime paidAt; private LocalDateTime shippedAt; private LocalDateTime confirmedAt; private LocalDateTime autoConfirmDueAt; private LocalDateTime settledAt;
    @Builder public EscrowTransaction(SecondhandProduct secondhandProduct, User buyerUser, User sellerUser, long amount, long feeAmount, EscrowStatus status){this.secondhandProduct=secondhandProduct; this.buyerUser=buyerUser; this.sellerUser=sellerUser; this.amount=amount; this.feeAmount=feeAmount; this.status=status==null?EscrowStatus.PAID:status; this.paidAt=LocalDateTime.now(); this.autoConfirmDueAt=LocalDateTime.now().plusHours(72);}    
    public void markShipped(String carrier, String trackingNumber){ this.carrier=carrier; this.trackingNumber=trackingNumber; this.status=EscrowStatus.SHIPPED; this.shippedAt=LocalDateTime.now(); }
    public void confirm(boolean auto){ this.status = auto ? EscrowStatus.AUTO_CONFIRMED : EscrowStatus.CONFIRMED; this.confirmedAt = LocalDateTime.now(); }
    public void settled(){ this.status = EscrowStatus.SETTLED; this.settledAt = LocalDateTime.now(); }
}
