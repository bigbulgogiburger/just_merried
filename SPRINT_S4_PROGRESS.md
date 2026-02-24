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

## Next
- S4-4 리뷰 API (등록/목록 + 평점 반영)
