# Sprint 8 종결 리포트

## 범위
- BE-8-01~08 핵심 골격 구현: 결제, 안전거래, 자동확정, 정산, 가격제안, 거래후기/매너온도, 비즈니스센터, 구독
- FE-8-01~07 핵심 화면 구현: 주문/결제, 안전거래, 주문관리, 가격제안/후기 컴포넌트, 비즈니스센터, 구독
- 문서 마감: 분해/진행/종결 문서 작성

## 구현 요약
### Backend
- 신규 패키지: `com.weddingmate.api.commerce`
  - `CommerceController`
  - `PaymentService`, `EscrowService`, `SettlementService`, `PriceOfferService`, `TradeReviewService`, `SubscriptionService`
  - `EscrowAutoConfirmScheduler`
- 신규 도메인: `com.weddingmate.domain.commerce`
  - Entity: `Payment`, `EscrowTransaction`, `Settlement`, `PriceOffer`, `TradeReview`, `Subscription`
  - Enum/Repository 세트
- Flyway: `V7__init_sprint8_payment_escrow_subscription.sql`
- ErrorCode 확장: 결제/에스크로/가격제안/구독 관련 코드 추가
- App 설정: `@EnableScheduling` 활성화

### Frontend
- API 클라이언트: `frontend/lib/api/s8.ts`
- 신규 페이지
  - `app/(main)/market/order/page.tsx`
  - `app/(main)/market/escrow/page.tsx`
  - `app/(main)/my/orders/page.tsx`
  - `app/(main)/my/subscription/page.tsx`
  - `app/business/page.tsx`
- 신규 컴포넌트
  - `components/market/PriceOfferModal.tsx`
  - `components/market/TradeReviewForm.tsx`

## 검증
- Backend: `./gradlew :domain:compileJava :api:compileJava` ✅
- Frontend: `npm run build` ✅

## 환경 준수 확인
- 백엔드 8082 / DB 5433 / local mock oauth 설정 변경 없음
- 무관 파일(`.serena/`, `AGENTS.md`, 임시 산출물) 커밋 제외

## 남은 리스크
- PG 연동은 mock 승인 방식으로 구현되어 실 PG webhook/서명 검증이 필요
- 비즈니스 센터 통계는 베이스 집계만 제공(실 주문 데이터 연동 고도화 필요)
- 후기/매너 온도 가중치 정책(±0.1~0.5 세밀 규칙)은 운영 정책 확정 후 재조정 필요
