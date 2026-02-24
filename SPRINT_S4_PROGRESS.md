# Sprint 4 진행 현황

## S4-1 완료 ✅ (데이터/도메인 기반)

### Flyway
- `backend/domain/src/main/resources/db/migration/V3__init_sprint4_vendor.sql`
  - `vendor_categories`, `vendors`, `vendor_images`, `vendor_prices`
  - `favorites`, `compare_items`, `reviews`
  - 카테고리/지역/가격/평점/생성일 인덱스 반영

### Domain Entity/Repository
- Entity
  - `VendorCategory`, `Vendor`, `VendorImage`, `VendorPrice`
  - `Favorite`, `CompareItem`, `Review`
  - `VendorStatus`
- Repository
  - `VendorCategoryRepository`, `VendorRepository`
  - `VendorImageRepository`, `VendorPriceRepository`
  - `FavoriteRepository`, `CompareItemRepository`, `ReviewRepository`

### 검증
- `./gradlew :domain:compileJava :api:compileJava` ✅

## S4-2 완료 ✅ (Vendor API 목록/상세/등록)

### API
- `GET /api/v1/vendors`
  - 필터: `categoryId`, `region`, `minPrice`, `maxPrice`
  - 정렬: `latest`, `rating`, `price_asc`, `price_desc`
- `GET /api/v1/vendors/{id}`
- `POST /api/v1/vendors` (관리용 최소 등록)

### 구현 포인트
- Vendor 목록 필터/정렬 처리
- 상세 응답에 이미지/가격 패키지 포함
- 카테고리/사용자 유효성 검증
- Vendor 에러코드 추가
  - `VD001 VENDOR_NOT_FOUND`
  - `VD002 VENDOR_CATEGORY_NOT_FOUND`

### 검증
- `./gradlew :api:compileJava` ✅

## S4-3 완료 ✅ (찜/비교 API)

### API
- 찜
  - `POST /api/v1/vendors/{id}/favorite`
  - `DELETE /api/v1/vendors/{id}/favorite`
- 비교함
  - `POST /api/v1/vendors/{id}/compare`
  - `DELETE /api/v1/vendors/{id}/compare`
  - `GET /api/v1/vendors/compare`

### 구현 포인트
- 사용자-업체 단위 중복 추가 방지
- 비교함 최대 5개 제한 적용
- 비교 목록 응답 DTO 제공 (`VendorCompareResponse`)
- 비교함 제한 에러코드 추가
  - `VD003 VENDOR_COMPARE_LIMIT_EXCEEDED`

### 검증
- `./gradlew :api:compileJava` ✅

## S4-4 완료 ✅ (리뷰 API)

### API
- `POST /api/v1/vendors/{id}/reviews`
- `GET /api/v1/vendors/{id}/reviews`

### 구현 포인트
- 리뷰 등록/목록 조회 구현
- 리뷰 등록 시 업체 평점 집계 반영
  - `rating_count`, `rating_avg` 갱신
- 리뷰 예외코드 추가
  - `VD004 REVIEW_NOT_FOUND` (후속 확장 대비)

### 검증
- `./gradlew :api:compileJava` ✅

## S4-5 완료 ✅ (FE 업체 리스트, Shadcn/UI)

### Frontend
- `app/(main)/vendors/page.tsx` 추가
  - 지역/가격 필터
  - 정렬(최신/평점/저가/고가)
  - 카드형 리스트
  - 페이지네이션(클라이언트)
- `lib/api/s4.ts` 추가
  - `listVendors` API 클라이언트

### 구현 포인트
- Shadcn 스타일 컴포넌트(`Card`, `Input`, `Button`, `Badge`) 적용
- 필터/정렬 조건을 API 파라미터로 전달
- 상세 페이지 진입 CTA 연결(`/vendors/{id}`)

### 검증
- `npm run build` ✅

## S4-6 완료 ✅ (FE 업체 상세 + 찜/비교/리뷰 연동)

### Frontend
- `app/(main)/vendors/[id]/page.tsx` 추가
  - 업체 상세 정보/대표이미지/기본 메타
  - 가격 패키지 목록 표시
  - 찜/비교 토글 액션 연동
  - 리뷰 등록/목록 연동
- `lib/api/s4.ts` 확장
  - `getVendorDetail`
  - `addFavorite`, `removeFavorite`
  - `addCompare`, `removeCompare`, `getCompareList`
  - `createReview`, `listReviews`

### 구현 포인트
- 상세 페이지에서 핵심 상호작용(찜/비교/리뷰) 원스톱 처리
- Shadcn 스타일 컴포넌트 조합 유지
- 리뷰 등록 후 목록/집계 즉시 반영

### 검증
- `npm run build` ✅

## S4-7 완료 ✅ (FE 비교함 화면)

### Frontend
- `app/(main)/vendors/compare/page.tsx` 추가
  - 비교 목록 조회
  - 비교 항목 제거
  - 업체 상세 이동
- `app/(main)/vendors/page.tsx` 업데이트
  - 비교함 진입 버튼 추가

### 구현 포인트
- 비교함을 독립 화면으로 분리해 의사결정 UX 강화
- 가격/평점/지역 핵심 항목 요약 표시
- 상세/제거 액션을 한 블록에 배치

### 검증
- `npm run build` ✅

## Next
- S4-8 마감 문서 + Sprint4 종결 리포트
