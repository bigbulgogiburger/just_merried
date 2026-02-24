package com.weddingmate.domain.commerce.entity;

import com.weddingmate.domain.config.BaseTimeEntity;
import com.weddingmate.domain.market.entity.SecondhandProduct;
import com.weddingmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Table(name="price_offers") @Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PriceOffer extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "secondhand_product_id", nullable = false) private SecondhandProduct secondhandProduct;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "buyer_user_id", nullable = false) private User buyerUser;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "seller_user_id", nullable = false) private User sellerUser;
    @Column(nullable = false) private long offeredPrice;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private OfferStatus status;
    @Column(length = 300) private String message;
    private LocalDateTime respondedAt;
    @Builder public PriceOffer(SecondhandProduct secondhandProduct, User buyerUser, User sellerUser, long offeredPrice, OfferStatus status, String message){this.secondhandProduct=secondhandProduct; this.buyerUser=buyerUser; this.sellerUser=sellerUser; this.offeredPrice=offeredPrice; this.status=status==null?OfferStatus.PENDING:status; this.message=message;}
    public void respond(OfferStatus status){ this.status=status; this.respondedAt=LocalDateTime.now(); }
}
