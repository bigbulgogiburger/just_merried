# Sprint 3 종결 리포트 (체크리스트 · 예산 · 일정)

작성일: 2026-02-24

## 1) Sprint 목표
- 결혼 준비 핵심 도메인(체크리스트/예산/일정) 백엔드 API 구현
- `/prepare` 허브에서 핵심 FE 관리 화면 연동
- Shadcn/ui 스타일 기반으로 UI 일관성 유지

## 2) 완료 범위

### Backend
- DB/도메인 스키마 확장
  - `V2__init_sprint3_wedding_prep.sql`
  - checklists, checklist_items, budgets, budget_categories, expenses, schedules
- 체크리스트 API
  - 생성/목록/수정/삭제
  - 항목 생성/수정/삭제
  - 완료 토글 + 진행률 계산
- 예산 API
  - 내 예산 upsert/요약
  - 카테고리 CRUD
  - 지출 CRUD + 기간 조회
  - 지출 변경 시 카테고리 spent 동기화
- 일정 API
  - 일정 CRUD
  - 기간(from/to) 조회

### Frontend
- `/prepare` 허브 고도화
  - 체크리스트 탭: 목록/상세/항목추가/완료토글
  - 예산 탭: 총예산 저장, 카테고리 추가, 요약 지표 표시
  - 일정 탭: 생성/목록/상태토글/삭제
- Shadcn/ui 톤 유지
  - Tabs, Card, Input, Badge, Button, Modal 조합

## 3) 주요 산출물 파일
- 진행 문서: `SPRINT_S3_PROGRESS.md`
- 종결 문서: `SPRINT_S3_CLOSE_REPORT.md`
- FE API: `frontend/lib/api/s3.ts`
- FE 화면: `frontend/app/(main)/prepare/page.tsx`
- BE API:
  - `backend/api/src/main/java/com/weddingmate/api/checklist/*`
  - `backend/api/src/main/java/com/weddingmate/api/budget/*`
  - `backend/api/src/main/java/com/weddingmate/api/schedule/*`

## 4) 검증
- Backend: `./gradlew :domain:compileJava :api:compileJava` ✅
- Frontend: `npm run build` ✅
- 사용자 요청에 따라 테스트 실행은 생략

## 5) Sprint 3 DoD 판정
- [x] 체크리스트/예산/일정 도메인 구축
- [x] 핵심 API 구현 완료
- [x] FE 허브 화면 연동 완료
- [x] Shadcn/ui 기반 UI 일관성 적용
- [x] 빌드 검증 통과 및 원격 반영 완료

## 6) Sprint 4 진입 권장 백로그
1. 업체(Vendor) 도메인/DDL/API 초안
2. 업체 검색/필터 인터페이스(카테고리/지역/가격)
3. 업체 상세(갤러리/가격/리뷰) 화면 골격
4. 비교함(최대 N개) 기본 UX + 상태 저장
