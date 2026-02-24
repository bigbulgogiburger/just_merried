# Sprint 8 진행 현황

## S8-1 완료 ✅ 도메인/스키마
- V7 마이그레이션 반영
- commerce 도메인(결제/에스크로/정산/가격제안/후기/구독) 엔티티/레포지토리 추가
- User 매너 온도 필드 반영

## S8-2 완료 ✅ 결제/안전거래/정산 API
- `POST /api/v1/commerce/payments`
- `POST /api/v1/commerce/payments/{paymentId}/cancel`
- `POST /api/v1/commerce/escrow`
- `PATCH /api/v1/commerce/escrow/{escrowId}/ship`
- `PATCH /api/v1/commerce/escrow/{escrowId}/confirm`
- `GET /api/v1/commerce/escrow/me`
- `GET /api/v1/commerce/settlements/me`
- `EscrowAutoConfirmScheduler` 추가 (시간 단위)

## S8-3 완료 ✅ 가격 제안/거래 후기
- `POST /api/v1/commerce/offers`
- `PATCH /api/v1/commerce/offers/{offerId}`
- `GET /api/v1/commerce/offers/me`
- `POST /api/v1/commerce/reviews`
- `GET /api/v1/commerce/reviews/{userId}`
- 후기 평균 점수 기반 매너 온도 업데이트

## S8-4 완료 ✅ 구독/비즈니스 센터 API
- `POST /api/v1/commerce/subscriptions`
- `GET /api/v1/commerce/subscriptions/me`
- `POST /api/v1/commerce/subscriptions/cancel`
- `POST /api/v1/commerce/business/products`
- `GET /api/v1/commerce/business/stats`

## S8-5 완료 ✅ FE 화면
- `/market/order`
- `/market/escrow`
- `/my/orders`
- `/my/subscription`
- `/business`
- `frontend/lib/api/s8.ts`
- `frontend/components/market/PriceOfferModal.tsx`
- `frontend/components/market/TradeReviewForm.tsx`

## 검증
- `./gradlew :domain:compileJava :api:compileJava` ✅
- `npm run build` ✅
