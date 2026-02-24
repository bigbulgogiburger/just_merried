package com.weddingmate.api.commerce;

import com.weddingmate.api.commerce.dto.PaymentDtos.*;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.domain.market.entity.MarketProduct;
import com.weddingmate.domain.market.entity.MarketProductStatus;
import com.weddingmate.domain.market.repository.MarketCategoryRepository;
import com.weddingmate.domain.market.repository.MarketProductRepository;
import com.weddingmate.domain.user.repository.UserRepository;
import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController @RequiredArgsConstructor @RequestMapping("/api/v1/commerce")
public class CommerceController {
    private final PaymentService paymentService; private final EscrowService escrowService; private final PriceOfferService priceOfferService; private final TradeReviewService tradeReviewService; private final SubscriptionService subscriptionService; private final SettlementService settlementService; private final MarketProductRepository marketProductRepository; private final MarketCategoryRepository marketCategoryRepository; private final UserRepository userRepository;

    @PostMapping("/payments") public ResponseEntity<ApiResponse<PaymentResponse>> requestPayment(@CurrentUser UserPrincipal p, @Valid @RequestBody PaymentRequest r){ return ResponseEntity.ok(ApiResponse.ok("결제를 요청했습니다.", paymentService.request(p.userId(), r))); }
    @PostMapping("/payments/{paymentId}/cancel") public ResponseEntity<ApiResponse<PaymentResponse>> cancelPayment(@PathVariable Long paymentId){ return ResponseEntity.ok(ApiResponse.ok("결제를 취소했습니다.", paymentService.cancel(paymentId))); }

    @PostMapping("/escrow") public ResponseEntity<ApiResponse<EscrowResponse>> createEscrow(@CurrentUser UserPrincipal p, @Valid @RequestBody EscrowCreateRequest r){ return ResponseEntity.ok(ApiResponse.ok("안전거래 결제를 완료했습니다.", escrowService.create(p.userId(), r))); }
    @PatchMapping("/escrow/{escrowId}/ship") public ResponseEntity<ApiResponse<EscrowResponse>> ship(@CurrentUser UserPrincipal p,@PathVariable Long escrowId,@Valid @RequestBody EscrowShipRequest r){ return ResponseEntity.ok(ApiResponse.ok("운송장 등록이 완료되었습니다.", escrowService.ship(p.userId(), escrowId, r))); }
    @PatchMapping("/escrow/{escrowId}/confirm") public ResponseEntity<ApiResponse<EscrowResponse>> confirm(@CurrentUser UserPrincipal p,@PathVariable Long escrowId){ return ResponseEntity.ok(ApiResponse.ok("수령 확인이 완료되었습니다.", escrowService.confirm(p.userId(), escrowId))); }
    @GetMapping("/escrow/me") public ResponseEntity<ApiResponse<List<EscrowResponse>>> myEscrow(@CurrentUser UserPrincipal p){ return ResponseEntity.ok(ApiResponse.ok(escrowService.myEscrow(p.userId()))); }

    @PostMapping("/offers") public ResponseEntity<ApiResponse<OfferResponse>> createOffer(@CurrentUser UserPrincipal p,@Valid @RequestBody OfferCreateRequest r){ return ResponseEntity.ok(ApiResponse.ok("가격 제안을 보냈습니다.", priceOfferService.create(p.userId(), r))); }
    @PatchMapping("/offers/{offerId}") public ResponseEntity<ApiResponse<OfferResponse>> respondOffer(@CurrentUser UserPrincipal p,@PathVariable Long offerId,@Valid @RequestBody OfferRespondRequest r){ return ResponseEntity.ok(ApiResponse.ok("가격 제안 응답이 반영되었습니다.", priceOfferService.respond(p.userId(), offerId, r))); }
    @GetMapping("/offers/me") public ResponseEntity<ApiResponse<List<OfferResponse>>> myOffers(@CurrentUser UserPrincipal p){ return ResponseEntity.ok(ApiResponse.ok(priceOfferService.myOffers(p.userId()))); }

    @PostMapping("/reviews") public ResponseEntity<ApiResponse<TradeReviewResponse>> createReview(@CurrentUser UserPrincipal p,@Valid @RequestBody TradeReviewCreateRequest r){ return ResponseEntity.ok(ApiResponse.ok("거래 후기를 등록했습니다.", tradeReviewService.create(p.userId(), r))); }
    @GetMapping("/reviews/{userId}") public ResponseEntity<ApiResponse<List<TradeReviewResponse>>> reviews(@PathVariable Long userId){ return ResponseEntity.ok(ApiResponse.ok(tradeReviewService.reviews(userId))); }

    @PostMapping("/subscriptions") public ResponseEntity<ApiResponse<SubscriptionResponse>> subscribe(@CurrentUser UserPrincipal p,@Valid @RequestBody SubscriptionRequest r){ return ResponseEntity.ok(ApiResponse.ok("구독이 활성화되었습니다.", subscriptionService.subscribe(p.userId(), r))); }
    @GetMapping("/subscriptions/me") public ResponseEntity<ApiResponse<SubscriptionResponse>> mySubscription(@CurrentUser UserPrincipal p){ return ResponseEntity.ok(ApiResponse.ok(subscriptionService.my(p.userId()))); }
    @PostMapping("/subscriptions/cancel") public ResponseEntity<ApiResponse<SubscriptionResponse>> cancelSubscription(@CurrentUser UserPrincipal p){ return ResponseEntity.ok(ApiResponse.ok("구독을 해지했습니다.", subscriptionService.cancel(p.userId()))); }

    @GetMapping("/settlements/me") public ResponseEntity<ApiResponse<List<SettlementResponse>>> mySettlements(@CurrentUser UserPrincipal p){ return ResponseEntity.ok(ApiResponse.ok(settlementService.mySettlements(p.userId()))); }

    @PostMapping("/business/products") @Transactional public ResponseEntity<ApiResponse<Long>> createBusinessProduct(@CurrentUser UserPrincipal p, @Valid @RequestBody BusinessProductRequest r){ var category = marketCategoryRepository.findById(r.categoryId()).orElseThrow(); var seller = userRepository.findById(p.userId()).orElseThrow(); MarketProduct mp = marketProductRepository.save(MarketProduct.builder().sellerUser(seller).category(category).name(r.name()).basePrice(r.basePrice()).description(r.description()).status(MarketProductStatus.ACTIVE).stockQuantity(999).build()); return ResponseEntity.ok(ApiResponse.ok("상품을 등록했습니다.", mp.getId())); }
    @GetMapping("/business/stats") public ResponseEntity<ApiResponse<Map<String,Object>>> businessStats(@CurrentUser UserPrincipal p){ return ResponseEntity.ok(ApiResponse.ok(Map.of("dailySales",0,"weeklySales",0,"monthlySales",0,"settlementCount", settlementService.mySettlements(p.userId()).size()))); }
}
