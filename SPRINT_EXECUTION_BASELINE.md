# Sprint Execution Baseline (2026-02-22)

## 목적
Sprint 1~2 착수를 위한 초기 상태 점검(부팅/빌드/테스트)과 즉시 리스크를 정리한다.

## 현재 점검 결과

### Backend (`backend/`)
- `./gradlew :api:test` ✅ (NO-SOURCE)
- `./gradlew test` ✅
- 멀티모듈 구성 확인: `common`, `domain`, `infra`, `api`

### Frontend (`frontend/`)
- 의존성 설치 `npm install` ✅
- `npm run build` ✅
- 초기 lint/type 오류 3건 수정 완료
  - `components/layout/bottom-nav.tsx`
  - `components/ui/button.tsx`
  - `components/ui/card.tsx`
- `next.config.js`에서 `typedRoutes` 실사용 라우트 미정 상태로 빌드 충돌하여 비활성화

### Mobile (`mobile/`)
- Flutter CLI PATH 미연결(현재 세션에서 `flutter` 명령 인식 실패) ⚠️
- 코드 스캐폴드 자체는 존재 (`lib/main.dart`, `pubspec.yaml`)

## 즉시 리스크
1. Flutter 개발 환경 PATH
   - 로컬 쉘에서 `flutter --version` 통과하도록 PATH 정리 필요
2. FE 보안 경고
   - 설치 시 Next 14.2.15 보안 경고 노출 → Sprint 1 내 패치 버전 업데이트 검토
3. 문서-구현 간 괴리 가능성
   - SPRINT_PLAN 범위가 매우 넓어, Sprint 1 DoD를 기능 단위로 더 좁혀야 함

## 다음 액션 (우선순위)
1. Sprint 1 상세 백로그 분해 (BE/FE/FL/DS/PB) — 2주 단위 산출물 확정
2. Auth/Onboarding 도메인 최소 API 계약(OpenAPI 초안) 먼저 고정
3. Flutter PATH 정리 후 `flutter analyze` 최소 1회 통과
4. 첫 커밋 생성(초기 스캐폴드 + 베이스라인 문서)
