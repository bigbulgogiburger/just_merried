# Sprint 7 진행 현황

## S7-1 완료 ✅ (도메인/스키마 기반)
- V6 마이그레이션 및 Market/Secondhand/Wishlist 도메인 baseline 반영

## S7-2 완료 ✅ B2B 마켓 조회 API
- `GET /api/v1/market/categories`
- `GET /api/v1/market/products`
- `GET /api/v1/market/products/{productId}`
- 재고/상태/이미지/옵션 응답 반영

## S7-3 완료 ✅ 중고거래 CRUD API
- `POST /api/v1/market/secondhand`
- `GET /api/v1/market/secondhand`
- `GET /api/v1/market/secondhand/{productId}`
- `PUT /api/v1/market/secondhand/{productId}`
- `DELETE /api/v1/market/secondhand/{productId}`
- `PATCH /api/v1/market/secondhand/{productId}/status`
- `GET /api/v1/market/secondhand/me`

## S7-4 완료 ✅ 관심 API
- `POST /api/v1/market/wishlist`
- `GET /api/v1/market/wishlist`
- `DELETE /api/v1/market/wishlist`

## S7-5 완료 ✅ 검색/발견 API
- `GET /api/v1/market/search`
- 키워드 + 지역/가격 필터
- latest/popular 탭 응답 모델 제공

## S7-6 ~ S7-8 완료 ✅ FE 화면
- `/market` 마켓 홈(신상품/중고 탭, 카테고리, 검색)
- `/market/[id]` B2B 상세
- `/market/secondhand/[id]` 중고 상세 + 관심 + DM 진입
- `/market/secondhand/new` 중고 등록
- `/my/wishlist` 관심 목록
- `/my/secondhand` 내 거래글 관리(상태변경/삭제)

## 검증
- `./gradlew :domain:compileJava :api:compileJava` ✅
- `npm run build` ✅
