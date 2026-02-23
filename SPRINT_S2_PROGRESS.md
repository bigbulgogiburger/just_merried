# Sprint 2 진행 현황

## 상태: 완료 ✅ (2026-02-24)

## 완료 범위

### Backend
- 온보딩 API
  - `POST /api/v1/auth/onboarding`
- 내 프로필 API
  - `GET /api/v1/users/me`
  - `PUT /api/v1/users/me`
- 커플 연동 API
  - `POST /api/v1/auth/couple/invite`
  - `POST /api/v1/auth/couple/connect`
  - `DELETE /api/v1/auth/couple`
- 비즈니스 가입/승인 API
  - `POST /api/v1/auth/business/register`
  - `GET /api/v1/admin/business/pending`
  - `PATCH /api/v1/admin/business/{id}/approve`
- 대시보드 요약 API
  - `GET /api/v1/dashboard/summary`

### Frontend
- `/login`, `/onboarding`, `/home`, `/my/profile`, `/my/couple` 구현 및 API 연동
- 인증 미들웨어 적용
- 전역 Toast 피드백 적용
- Tabs/Modal/BottomSheet 기반 UI 고도화

## 검증
- `./gradlew :api:compileJava` ✅
- `npm run build` ✅
- 사용자 요청에 따라 테스트 실행은 생략

## 산출물 문서
- `frontend/docs/ui/SHADCN_UI_도입기획.md`
- `SPRINT_S2_CLOSE_REPORT.md`

## 다음 작업 (Sprint 3)
- 체크리스트/예산/일정 도메인 및 API 착수
