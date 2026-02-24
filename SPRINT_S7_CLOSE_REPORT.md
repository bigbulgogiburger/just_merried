# Sprint 7 종결 리포트

## 범위
- S7-2 B2B 조회 API
- S7-3 중고 CRUD API
- S7-4 관심 API
- S7-5 검색/발견 API
- S7-6~S7-8 FE 마켓/상세/등록/관심/내거래 화면
- S7-9 문서 마감

## 구현 요약
### Backend
- `com.weddingmate.api.market` 신규 구성
  - `MarketController`
  - `MarketQueryService`
  - `SecondhandCommandService`
  - `WishlistService`
- Market/Secondhand/Wishlist DTO 세트 추가
- 권한 검증(작성자 검증), 판매 상태 변경, 키워드 검색/필터 반영
- ErrorCode 확장
  - `MARKET_CATEGORY_NOT_FOUND`, `MARKET_PRODUCT_NOT_FOUND`, `SECONDHAND_PRODUCT_NOT_FOUND`, `SECONDHAND_PRODUCT_ACCESS_DENIED`

### Frontend
- API 클라이언트: `frontend/lib/api/s7.ts`
- 신규 화면
  - `app/(main)/market/page.tsx`
  - `app/(main)/market/[id]/page.tsx`
  - `app/(main)/market/secondhand/[id]/page.tsx`
  - `app/(main)/market/secondhand/new/page.tsx`
  - `app/(main)/my/wishlist/page.tsx`
  - `app/(main)/my/secondhand/page.tsx`
- S6 DM API 재사용으로 중고 상세에서 판매자 DM 진입 연결

## 검증
- Backend: `./gradlew :domain:compileJava :api:compileJava` ✅
- Frontend: `npm run build` ✅

## 환경 준수 확인
- 백엔드 포트 8082 / DB 5433 / 로컬 mock oauth 기존 설정 유지
- 무관 파일(`.serena/`, `AGENTS.md`) 커밋 제외 예정

## 남은 리스크
- 검색 인기(popular) 정렬은 현재 latest 결과 재사용(추후 조회수/찜수 기반 랭킹 고도화 필요)
- FE 중고 수정 전용 폼은 미분리(등록 폼 중심으로 우선 제공)
- 위시리스트 목록은 공통 포맷이며 상세 조인 데이터(제목/썸네일) 확장은 차기 보완 필요
