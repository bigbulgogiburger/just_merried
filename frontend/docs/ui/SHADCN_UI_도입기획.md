# WeddingMate FE Shadcn/UI 도입 기획 (S2 병행)

## 목적
- S2 화면을 빠르게 구현하면서 UI 품질 일관성을 확보한다.
- "디자인 토큰(브랜드) + shadcn 컴포넌트(구조)" 혼합 전략으로 개발 속도를 높인다.

## 현재 상태 진단
- 이미 `Button`, `Card`, `Input`, `Badge`, `Toast`, `Modal` 등 기초 컴포넌트가 존재.
- Tailwind 토큰과 브랜드 색상이 커스텀되어 있어 완전 교체보다 점진적 정렬이 효율적.

## 도입 전략
1. **1단계 (이번 S2-2)**: shadcn 규약 정렬
   - 파일 구조/이름/variant 규칙을 shadcn 스타일로 맞춤
   - 폼/레이아웃 페이지에 공통 UI 컴포넌트 우선 적용
2. **2단계 (S3)**: 복합 컴포넌트 확장
   - `Dialog`, `Sheet`, `Tabs`, `Dropdown`, `Command`, `DataTable` 도입
3. **3단계 (S4)**: 접근성/다크모드/테마 확장
   - keyboard nav, aria 점검, 테마 전환

## S2-2 적용 대상
- 로그인
- 온보딩
- 마이 프로필
- 커플 연동
- 홈 대시보드 카드

## 컴포넌트 정책
- 기본 원칙: shadcn composable + 도메인 wrapper
- 예시
  - 공통: `components/ui/*`
  - 도메인: `components/feature/onboarding/*`
- Variant 규칙
  - Button: `primary | secondary | outline | ghost`
  - Badge: `default | info | success | warning | error | outline`

## 디자인 품질 가이드
- 간격 단위 4px 스케일 준수
- 텍스트 hierarchy: 제목(24/20), 본문(16), 보조(14/12)
- 상태피드백 즉시 노출(성공/오류)
- 모바일 우선(max-width 기준) + tablet/desktop 점진 확장

## 완료 기준 (S2)
- S2 핵심 화면이 공통 컴포넌트만으로 구현됨
- 동일 액션 버튼/입력 폼의 스타일 불일치 0건
- 신규 화면은 반드시 `components/ui` 기반으로 작성
