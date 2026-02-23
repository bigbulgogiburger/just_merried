# Sprint 2 종결 리포트 (Auth 완성 · 온보딩 · 커플 연동)

작성일: 2026-02-24

## 1) Sprint 목표
- 인증 흐름 완성(로그인/토큰/미들웨어)
- 온보딩/프로필/커플 연동 API 및 화면 구현
- 비즈니스 가입/관리자 승인 흐름 기반 구축
- 홈 대시보드 최소 지표 제공

## 2) 구현 완료 범위

### Backend
- Auth
  - `POST /api/v1/auth/login/{provider}`
  - `POST /api/v1/auth/refresh`
  - `POST /api/v1/auth/logout`
- Onboarding / Profile
  - `POST /api/v1/auth/onboarding`
  - `GET /api/v1/users/me`
  - `PUT /api/v1/users/me`
- Couple
  - `POST /api/v1/auth/couple/invite`
  - `POST /api/v1/auth/couple/connect`
  - `DELETE /api/v1/auth/couple`
- Business + Admin
  - `POST /api/v1/auth/business/register`
  - `GET /api/v1/admin/business/pending`
  - `PATCH /api/v1/admin/business/{id}/approve`
- Dashboard
  - `GET /api/v1/dashboard/summary`

### Frontend
- 인증/라우팅
  - `/login`, `middleware.ts` (비인증 리다이렉트)
- 온보딩
  - `/onboarding` (탭 기반 4단계 UX)
- 홈
  - `/home` (D-Day/진행률/상태 메시지)
- 마이
  - `/my/profile` (아바타/프로필 편집)
  - `/my/couple` (탭 + 모달 + 바텀시트)
- 공통 UX
  - 전역 토스트 프로바이더 적용
  - Shadcn 스타일 컴포넌트 확장(`Tabs`)

## 3) UI/기획 반영
- 문서: `frontend/docs/ui/SHADCN_UI_도입기획.md`
- 적용 결과:
  - 온보딩/커플연동의 정보 구조 개선(Tabs)
  - 위험 액션 확인 모달(연동 해제)
  - 모바일 공유 플로우(BottomSheet)
  - 홈/프로필 정보카드 정돈

## 4) 검증 결과
- Backend: `./gradlew :api:compileJava` ✅
- Frontend: `npm run build` ✅
- 주의: 사용자 요청에 따라 테스트 명령은 수행하지 않음

## 5) Sprint 2 DoD 판정
- [x] 핵심 API 구현 및 인증 경로 연결
- [x] 핵심 FE 화면 구현 및 API 연동
- [x] UI 일관성(컴포넌트/상태피드백) 확보
- [x] 빌드 검증 통과
- [x] 원격 main 반영 완료

## 6) Sprint 3 진입 백로그(즉시 착수용)
1. 체크리스트 도메인/API 설계 및 DDL 초안
2. 예산 도메인/API 설계 및 통계 응답 계약
3. 홈 위젯과 체크리스트/예산 실데이터 연동
4. 캘린더 일정 기본 CRUD 화면/엔드포인트 착수
