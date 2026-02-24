# Sprint 8 세부 작업 분해 (결제 & 안전거래)

## 목표
- S7 마켓/중고 흐름 위에 결제/안전거래/정산/구독 핵심 도메인 연결
- 구매자-판매자 거래를 결제→발송→수령확인→정산으로 닫는 기본 플로우 확보
- FE에 주문/에스크로/구독/비즈니스 센터 베이스 화면 추가

## S8-1 도메인/스키마
- [x] Flyway V7: payments, escrow_transactions, settlements, price_offers, trade_reviews, subscriptions
- [x] User manner_temperature 컬럼 및 엔티티 반영
- [x] commerce 도메인 엔티티/레포지토리 추가
- 검증: `./gradlew :domain:compileJava :api:compileJava`

## S8-2 결제/에스크로/정산 API
- [x] 결제 요청/취소 API (mock 승인)
- [x] 에스크로 생성/발송 등록/수령 확인 API
- [x] 정산 생성(수수료 3.5%) 및 내역 조회 API
- [x] 72시간 자동확정 스케줄러
- 검증: `./gradlew :api:compileJava`

## S8-3 가격 제안/거래 후기/매너 온도 API
- [x] 가격 제안 생성/응답/내 목록 API
- [x] 거래 후기 등록/조회 API
- [x] 후기 평균 점수 기반 매너 온도 반영
- 검증: `./gradlew :api:compileJava`

## S8-4 구독/비즈니스 센터 API
- [x] Basic 구독 활성화/조회/해지 API
- [x] 비즈니스 상품 등록 API
- [x] 비즈니스 통계(기본 집계) API
- 검증: `./gradlew :api:compileJava`

## S8-5 FE 주문/안전거래/마이 주문/구독/비즈니스
- [x] `/market/order`
- [x] `/market/escrow`
- [x] `/my/orders`
- [x] `/my/subscription`
- [x] `/business`
- [x] 공통 API 클라이언트 `lib/api/s8.ts`
- [x] 재사용 컴포넌트 `PriceOfferModal`, `TradeReviewForm`
- 검증: `npm run build`

## S8-6 마감 문서
- [x] `SPRINT_S8_PROGRESS.md` 업데이트
- [x] `SPRINT_S8_CLOSE_REPORT.md` 작성
- [x] 최종 컴파일/빌드 재검증
