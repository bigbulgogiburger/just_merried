# Sprint 7 진행 현황

## S7-1 완료 ✅ (도메인/스키마 기반)

### Flyway
- `backend/domain/src/main/resources/db/migration/V6__init_sprint7_marketplace_secondhand.sql`
  - B2B 마켓: `market_categories`, `market_products`, `market_product_options`, `market_product_images`
  - 중고거래: `secondhand_products`, `secondhand_product_images`
  - 공통 관심: `market_wishlists`
  - 조회 성능 인덱스 반영

### Domain Entity/Repository
- Entity
  - `MarketCategory`, `MarketProduct`, `MarketProductOption`, `MarketProductImage`
  - `SecondhandProduct`, `SecondhandProductImage`
  - `MarketWishlist`
  - enum: `MarketProductStatus`, `SecondhandConditionStatus`, `SecondhandTradeMethod`, `SecondhandSaleStatus`, `WishlistTargetType`
- Repository
  - `MarketCategoryRepository`, `MarketProductRepository`, `MarketProductOptionRepository`, `MarketProductImageRepository`
  - `SecondhandProductRepository`, `SecondhandProductImageRepository`
  - `MarketWishlistRepository`

### 검증
- `./gradlew :domain:compileJava :api:compileJava` ✅

## Next
- S7-2 B2B 마켓 조회 API (카테고리/목록/상세)
