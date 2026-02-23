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

## Next
- S4-2 Vendor API (목록/상세/관리용 최소 등록)
