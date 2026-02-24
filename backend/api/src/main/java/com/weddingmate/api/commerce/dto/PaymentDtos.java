package com.weddingmate.api.commerce.dto;

import com.weddingmate.domain.commerce.entity.*;
import com.weddingmate.domain.user.entity.User;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class PaymentDtos {
    public record PaymentRequest(@NotBlank String orderType, Long orderId, @Min(100) long amount, @NotBlank String paymentMethod, String provider) {}
    public record PaymentResponse(Long id, String orderType, Long orderId, long amount, String paymentMethod, String provider, PaymentStatus status, LocalDateTime approvedAt, LocalDateTime createdAt) {
        public static PaymentResponse from(Payment p){ return new PaymentResponse(p.getId(), p.getOrderType(), p.getOrderId(), p.getAmount(), p.getPaymentMethod(), p.getProvider(), p.getStatus(), p.getApprovedAt(), p.getCreatedAt()); }
    }
    public record EscrowCreateRequest(@NotNull Long secondhandProductId, @Min(100) long amount) {}
    public record EscrowShipRequest(@NotBlank String carrier, @NotBlank String trackingNumber) {}
    public record EscrowResponse(Long id, Long secondhandProductId, Long buyerUserId, Long sellerUserId, long amount, EscrowStatus status, String carrier, String trackingNumber, LocalDateTime autoConfirmDueAt, LocalDateTime createdAt) {
        public static EscrowResponse from(EscrowTransaction e){ return new EscrowResponse(e.getId(), e.getSecondhandProduct().getId(), e.getBuyerUser().getId(), e.getSellerUser().getId(), e.getAmount(), e.getStatus(), e.getCarrier(), e.getTrackingNumber(), e.getAutoConfirmDueAt(), e.getCreatedAt()); }
    }
    public record OfferCreateRequest(@NotNull Long secondhandProductId, @Min(100) long offeredPrice, String message) {}
    public record OfferRespondRequest(@NotNull OfferStatus status) {}
    public record OfferResponse(Long id, Long secondhandProductId, Long buyerUserId, Long sellerUserId, long offeredPrice, OfferStatus status, String message, LocalDateTime createdAt) {
        public static OfferResponse from(PriceOffer o){ return new OfferResponse(o.getId(), o.getSecondhandProduct().getId(), o.getBuyerUser().getId(), o.getSellerUser().getId(), o.getOfferedPrice(), o.getStatus(), o.getMessage(), o.getCreatedAt()); }
    }
    public record TradeReviewCreateRequest(@NotNull Long escrowTransactionId, @NotNull Long revieweeUserId, @Min(1) @Max(5) int scoreKindness, @Min(1) @Max(5) int scorePunctuality, @Min(1) @Max(5) int scoreQuality, String content) {}
    public record TradeReviewResponse(Long id, Long revieweeUserId, double averageScore, String content, LocalDateTime createdAt) {
        public static TradeReviewResponse from(TradeReview r){ return new TradeReviewResponse(r.getId(), r.getRevieweeUser().getId(), r.averageScore(), r.getContent(), r.getCreatedAt()); }
    }
    public record SubscriptionRequest(@NotNull SubscriptionPlanType planType, @NotNull Long paymentId) {}
    public record SubscriptionResponse(Long id, Long userId, SubscriptionPlanType planType, SubscriptionStatus status, boolean autoRenew, LocalDateTime renewAt) {
        public static SubscriptionResponse from(Subscription s){ return new SubscriptionResponse(s.getId(), s.getUser().getId(), s.getPlanType(), s.getStatus(), s.isAutoRenew(), s.getRenewAt()); }
    }
    public record SettlementResponse(Long id, Long sellerUserId, SettlementType settlementType, long grossAmount, long feeAmount, long netAmount, SettlementStatus status, LocalDateTime settledAt) {
        public static SettlementResponse from(Settlement s){ return new SettlementResponse(s.getId(), s.getSellerUser().getId(), s.getSettlementType(), s.getGrossAmount(), s.getFeeAmount(), s.getNetAmount(), s.getStatus(), s.getSettledAt()); }
    }
    public record BusinessProductRequest(@NotBlank String name, @Min(0) long basePrice, String description, @NotNull Long categoryId) {}
}
