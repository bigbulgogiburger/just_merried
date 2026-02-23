# Sprint 2 진행 현황

## S2-1 (Backend API 착수) 완료

### 구현 범위
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

### 핵심 구현 포인트
- JWT `@CurrentUser` 기반 사용자 식별
- 6자리 커플 초대코드 생성 및 중복 방지
- 비즈니스 승인 시 사용자 역할 `VENDOR` 승격
- 에러코드 기반 예외 처리(`ErrorCode`) 일관화

### 검증
- 테스트 실행은 사용자 지시로 생략
- 컴파일 검증만 수행
  - `./gradlew :api:compileJava` ✅

## 다음 작업(S2-2)
- FE 온보딩/프로필/커플연동 화면 API 연결
- 홈 대시보드(D-Day/진행률) 최소 위젯 반영
- OpenAPI 문서 동기화
