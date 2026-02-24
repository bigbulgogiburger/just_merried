# Sprint 4 종결 리포트 (업체 탐색 · 상세 · 비교)

작성일: 2026-02-24

## 1) Sprint 목표
- 업체 탐색/상세/비교의 핵심 사용자 흐름 완성
- 찜/비교/리뷰 상호작용 API 및 화면 연결
- Shadcn/ui 기반으로 UI 일관성 유지

## 2) 완료 범위

### Backend
- 도메인/스키마 기반
  - `V3__init_sprint4_vendor.sql`
  - vendor_categories, vendors, vendor_images, vendor_prices, favorites, compare_items, reviews
- Vendor API
  - `GET /api/v1/vendors` (필터/정렬)
  - `GET /api/v1/vendors/{id}`
  - `POST /api/v1/vendors` (관리용 최소 등록)
- 찜/비교 API
  - `POST/DELETE /api/v1/vendors/{id}/favorite`
  - `POST/DELETE /api/v1/vendors/{id}/compare`
  - `GET /api/v1/vendors/compare`
  - 비교함 최대 5개 제한
- 리뷰 API
  - `POST /api/v1/vendors/{id}/reviews`
  - `GET /api/v1/vendors/{id}/reviews`
  - 리뷰 등록 시 vendor 평점 집계 반영

### Frontend
- 업체 리스트: `/vendors`
  - 지역/가격 필터, 정렬, 카드 리스트, 페이지네이션
- 업체 상세: `/vendors/[id]`
  - 상세정보, 이미지, 가격패키지
  - 찜/비교 액션
  - 리뷰 등록/목록
- 비교함: `/vendors/compare`
  - 비교 목록 조회
  - 제거/상세 이동

## 3) 주요 산출물 파일
- `SPRINT_S4_BREAKDOWN.md`
- `SPRINT_S4_PROGRESS.md`
- `SPRINT_S4_CLOSE_REPORT.md`
- Backend
  - `backend/domain/src/main/resources/db/migration/V3__init_sprint4_vendor.sql`
  - `backend/domain/src/main/java/com/weddingmate/domain/vendor/*`
  - `backend/api/src/main/java/com/weddingmate/api/vendor/*`
- Frontend
  - `frontend/lib/api/s4.ts`
  - `frontend/app/(main)/vendors/page.tsx`
  - `frontend/app/(main)/vendors/[id]/page.tsx`
  - `frontend/app/(main)/vendors/compare/page.tsx`

## 4) 검증
- Backend 컴파일
  - `./gradlew :domain:compileJava :api:compileJava` ✅
- Frontend 빌드
  - `npm run build` ✅
- 사용자 요청에 따라 테스트 명령은 수행하지 않음

## 5) Sprint 4 DoD 판정
- [x] Vendor/찜/비교/리뷰 도메인/API 구현
- [x] 업체 리스트/상세/비교 FE 연동
- [x] 핵심 액션(찜/비교/리뷰) 사용자 플로우 연결
- [x] 빌드/컴파일 검증 통과
- [x] main 원격 반영 완료

## 6) Sprint 5 진입 권장 백로그
1. SNS 게시글 도메인/DDL/API 초안 (post/media/hashtag)
2. 좋아요/댓글/팔로우 API 및 카운트 정책 정의
3. 커뮤니티 피드 리스트 UI + 작성 흐름 1차 구현
4. 알림센터 연동 전략(좋아요/댓글/팔로우 이벤트) 수립
