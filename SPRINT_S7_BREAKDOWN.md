# Sprint 7 세부 작업 분해 (Marketplace & SecondHand)

## 목표
- B2B 마켓(신상품) + 중고거래 핵심 기능을 하나의 사용자 흐름으로 연결
- 결제는 제외하고(차기 스프린트), 탐색/상세/등록/관심/채팅 연계까지 구현

## S7-1 도메인/스키마 기반 (B2B + 중고)
- [ ] Flyway: 마켓 카테고리/상품/옵션/이미지/재고
- [ ] Flyway: 중고상품/상품이미지/상태/거래방식/지역
- [ ] Flyway: 관심상품(위시리스트) 공통 테이블
- [ ] 엔티티/리포지토리 baseline
- 검증: `./gradlew :domain:compileJava :api:compileJava`

## S7-2 B2B 마켓 조회 API
- [ ] 카테고리 목록
- [ ] 상품 목록(정렬/필터/페이지네이션)
- [ ] 상품 상세(이미지/옵션/기본 리뷰 요약)
- [ ] 재고 상태 필드 반영
- 검증: `./gradlew :api:compileJava`

## S7-3 중고거래 CRUD API
- [ ] 중고상품 등록
- [ ] 중고상품 목록/상세
- [ ] 수정/삭제(작성자 검증)
- [ ] 상태 변경(판매중/예약중/거래완료)
- 검증: `./gradlew :api:compileJava`

## S7-4 관심상품/즐겨찾기 API
- [ ] B2B/중고 공통 관심등록
- [ ] 내 관심목록 조회
- [ ] 관심 해제
- 검증: `./gradlew :api:compileJava`

## S7-5 검색/발견 API
- [ ] 통합 검색(키워드 기반, B2B+중고)
- [ ] 카테고리/지역/가격대 필터
- [ ] 인기/최신 탭 응답 모델
- 검증: `./gradlew :api:compileJava`

## S7-6 FE 마켓 홈/목록
- [ ] `/market` 홈 (신상품/중고 탭)
- [ ] 카테고리 아이콘/상품 카드
- [ ] 정렬/필터/무한스크롤 또는 페이지네이션
- 검증: `npm run build`

## S7-7 FE 상세/등록 화면
- [ ] B2B 상품 상세
- [ ] 중고상품 상세
- [ ] 중고 등록/수정 폼(이미지/설명/가격/지역/거래방식)
- 검증: `npm run build`

## S7-8 FE 관심/내 거래
- [ ] 관심상품 페이지
- [ ] 내 중고 등록글 관리(상태변경/수정/삭제)
- [ ] DM 진입 버튼 연계(S6 기능 재사용)
- 검증: `npm run build`

## S7-9 마감 문서
- [ ] `SPRINT_S7_PROGRESS.md` 업데이트
- [ ] `SPRINT_S7_CLOSE_REPORT.md` 작성
- [ ] 최종 컴파일/빌드 재검증
  - `./gradlew :domain:compileJava :api:compileJava`
  - `npm run build`

---

## 실행 원칙
- 작은 단위 구현 → 즉시 검증 → commit/push
- 무관 파일(`.serena/`, `AGENTS.md`) 커밋 제외
- 결제/PG 연동은 S7 범위에서 제외 (S8 이상으로 이관)
