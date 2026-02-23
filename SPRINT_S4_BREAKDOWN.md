# Sprint 4 작업 분해 (업체 탐색 & 비교)

작성일: 2026-02-24

## 목표
- 업체(Vendor) 탐색, 상세, 비교함의 기본 사용자 흐름 완성
- 리뷰/찜/비교 기능의 MVP API + FE 화면 연결

---

## S4-1 데이터/도메인 기반
- Vendor 관련 DDL/Flyway
  - vendors, vendor_categories, vendor_images, vendor_prices
  - favorites(찜), compare_items(비교함), reviews(기본)
- 도메인 엔티티/리포지토리 생성
- 인덱스(카테고리/지역/가격/평점/created_at)

## S4-2 Vendor API (목록/상세)
- `GET /api/v1/vendors` (카테고리/지역/가격/정렬/페이징)
- `GET /api/v1/vendors/{id}` (상세)
- (관리용) `POST /api/v1/vendors` 최소 등록

## S4-3 찜/비교 API
- 찜 토글
  - `POST /api/v1/vendors/{id}/favorite`
  - `DELETE /api/v1/vendors/{id}/favorite`
- 비교함
  - `POST /api/v1/vendors/{id}/compare`
  - `DELETE /api/v1/vendors/{id}/compare`
  - `GET /api/v1/vendors/compare`
- 비교함 최대 개수 제한(예: 5개)

## S4-4 리뷰 API (MVP)
- `POST /api/v1/vendors/{id}/reviews`
- `GET /api/v1/vendors/{id}/reviews`
- 평점 평균/카운트 계산 반영

## S4-5 FE 업체 리스트 (Shadcn/ui)
- `/prepare/vendors` 혹은 `/wedding/vendors` 라우트 추가
- 필터 바(카테고리/지역/가격), 정렬, 카드 리스트
- 무한스크롤 대신 S4에서는 페이지네이션 우선

## S4-6 FE 업체 상세
- 갤러리, 가격표, 리뷰 영역
- 찜/비교 CTA 버튼
- 비교함 추가 상태 즉시 반영

## S4-7 FE 비교함
- 비교 목록/제거
- 핵심 비교 항목(가격/평점/지역)

## S4-8 마감
- Sprint4 진행문서/종결리포트
- 검증 결과(컴파일+프론트 빌드)

---

## 실행 순서(고정)
1. S4-1
2. S4-2
3. S4-3
4. S4-4
5. S4-5
6. S4-6
7. S4-7
8. S4-8
