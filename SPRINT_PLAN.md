# WeddingMate Sprint Plan (5개월 / 10 Sprints)

> **기간**: 2026-03-02 ~ 2026-07-31 (22주)
> **스프린트 단위**: 2주 (총 10 스프린트 + 버퍼 2주)
> **팀 구성**: Backend(BE) / Frontend(FE) / Flutter App(FL) / Designer(DS) / Publisher(PB)
> **작성일**: 2026-02-22

---

## 목차

- [전체 타임라인 개요](#전체-타임라인-개요)
- [역할별 기술 스택](#역할별-기술-스택)
- [Sprint 1~2: Foundation](#phase-1-foundation-sprint-12)
- [Sprint 3~4: Wedding Prep Core](#phase-2-wedding-prep-core-sprint-34)
- [Sprint 5~6: SNS & Community](#phase-3-sns--community-sprint-56)
- [Sprint 7~8: Marketplace & SecondHand](#phase-4-marketplace--secondhand-sprint-78)
- [Sprint 9~10: Married Life & Polish](#phase-5-married-life--polish-sprint-910)
- [버퍼 기간: QA & Launch Prep](#버퍼-기간-qa--launch-prep)
- [마일스톤 요약](#마일스톤-요약)
- [리스크 관리](#리스크-관리)
- [Definition of Done](#definition-of-done)

---

## 전체 타임라인 개요

```
Month 1        Month 2        Month 3        Month 4        Month 5        Buffer
[S1][S2]       [S3][S4]       [S5][S6]       [S7][S8]       [S9][S10]      [QA]
 │    │          │    │          │    │          │    │          │    │        │
 ▼    ▼          ▼    ▼          ▼    ▼          ▼    ▼          ▼    ▼        ▼
인프라 Auth     체크   예산     SNS   SNS      마켓  중고      결혼   QA     런칭
셋업  커플     리스트 업체     피드  채팅      B2B  거래      생활  최적화   준비
DB    연동     일정   탐색     검색  DM       결제  에스크로  가족   성능
```

### 기능 우선순위 압축 전략 (12개월 → 5개월)

| 범위 | 포함 (MVP) | 제외 (Post-MVP) |
|------|-----------|----------------|
| Auth | 소셜 로그인 4종, 커플 연동, 비즈니스 가입 | 본인 인증 배지 |
| 결혼 준비 | 체크리스트, 예산, 일정, 업체 탐색 | AI 추천, 신혼여행 플래너 |
| 결혼 생활 | 가족 캘린더, 신혼집 체크리스트 | 가계부, 임신/육아 상세 |
| SNS | 피드, 좋아요/댓글, 팔로우, 해시태그, DM | 스토리, 그룹채팅 |
| 마켓 | B2B 입점, 상품 관리, 주문/결제 | 기획전, 브랜드관 |
| 중고거래 | 등록, 검색, 채팅, 안전거래 | 끌어올리기, 프리미엄 게시 |
| 구독 | Free / Basic 2단계 | Premium, Family |
| 광고 | 검색 광고 기본 | 홈 배너, 네이티브, 푸시 광고 |

---

## 역할별 기술 스택

### Backend (BE)

| 항목 | 기술 |
|------|------|
| Framework | Spring Boot 3.3.x (Java 21, Gradle) |
| DB | PostgreSQL 16 + Flyway (마이그레이션) |
| Cache | Redis 7 |
| Search | Elasticsearch 8 |
| Message Queue | Apache Kafka 3 |
| Real-time | WebSocket (STOMP + SockJS) |
| Storage | AWS S3 + CloudFront |
| Auth | JWT (RS256) + Spring Security + OAuth2 Client |
| API Spec | OpenAPI 3.0 (springdoc-openapi) |
| Test | JUnit 5, Mockito, Testcontainers |
| CI/CD | GitHub Actions + Docker + EKS |

### Frontend (FE)

| 항목 | 기술 |
|------|------|
| Framework | Next.js 14 (App Router) |
| Language | TypeScript 5 |
| State | Zustand + TanStack Query v5 |
| Styling | Tailwind CSS 3 + shadcn/ui |
| Form | React Hook Form + Zod |
| Real-time | Socket.io Client / STOMP.js |
| Test | Vitest + Testing Library + Playwright (E2E) |
| Lint/Format | ESLint + Prettier |

### Flutter App (FL)

| 항목 | 기술 |
|------|------|
| Framework | Flutter 3.x (Dart 3) |
| State | Riverpod 2 + freezed |
| Network | Dio + Retrofit |
| Local DB | Hive / Isar |
| Push | Firebase Cloud Messaging |
| Image | cached_network_image + image_picker |
| Real-time | stomp_dart_client |
| Navigation | go_router |
| Test | flutter_test + integration_test |

### Designer (DS)

| 항목 | 도구 |
|------|------|
| UI/UX | Figma |
| 디자인 시스템 | Figma Component Library |
| 프로토타이핑 | Figma Prototype |
| 아이콘 | Figma + SVG Export |
| Handoff | Figma Dev Mode |

### Publisher (PB)

| 항목 | 역할 |
|------|------|
| 마크업 | Figma → HTML/CSS(Tailwind) 변환 |
| 반응형 | Mobile-first 반응형 구현 |
| 애니메이션 | Framer Motion / CSS Transition |
| 접근성 | WCAG 2.1 AA 기준 |
| 크로스브라우저 | Chrome, Safari, Samsung Internet |

---

## Phase 1: Foundation (Sprint 1~2)

> **목표**: 인프라 구축, 인증 시스템, 온보딩, 커플 연동까지 완성

### Sprint 1 (Week 1~2) — 인프라 & 프로젝트 셋업

#### DS (Designer)

| Task ID | 작업 내용 | 산출물 | 비고 |
|---------|----------|--------|------|
| DS-1-01 | 디자인 시스템 정의 | Color Palette, Typography Scale, Spacing/Grid System, Shadow/Radius Token | 브랜드 컬러: 웨딩 감성(로즈골드 + 아이보리 계열 추천) |
| DS-1-02 | 공통 컴포넌트 디자인 | Button(4종), Input(5종), Card, Modal, Bottom Sheet, Toast, Badge, Chip, Avatar | 모든 상태(default/hover/active/disabled/error) 포함 |
| DS-1-03 | 아이콘 시스템 정의 | 아이콘 24x24 기본, 라인/솔리드 2세트, Bottom Tab 아이콘 5종 | SVG Export 규격 통일 |
| DS-1-04 | 스플래시 / 온보딩 화면 디자인 | 스플래시(로고 애니메이션), 온보딩 4단계 슬라이드, 결혼 상태 선택 UI | 일러스트 포함 |
| DS-1-05 | 소셜 로그인 화면 디자인 | 로그인 화면, 소셜 버튼 4종(카카오/네이버/구글/Apple), 약관 동의 화면 | 각 소셜 브랜드 가이드 준수 |
| DS-1-06 | Bottom Navigation 디자인 | 5개 탭(홈/결혼준비/커뮤니티/마켓/MY), Selected/Unselected 상태 | 알림 뱃지 포함 |
| DS-1-07 | 홈 화면 디자인 | 결혼 준비 대시보드 / 결혼 생활 대시보드 2종 | D-Day 위젯, 진행률, 오늘의 할 일 |

#### BE (Backend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| BE-1-01 | 프로젝트 초기화 | Spring Boot 3.3.x + Java 21 + Gradle 멀티모듈 구성 | `api`, `domain`, `infra`, `common` 모듈 |
| BE-1-02 | DB 설계 & 마이그레이션 셋업 | PostgreSQL 16 + Flyway 연동, `users`, `couples`, `couple_members`, `user_settings`, `business_profiles` DDL | V1__init_user_domain.sql |
| BE-1-03 | Redis 셋업 | Redis 7 연동, Spring Data Redis, 세션/캐시 설정 | RedisConfig.java, 커넥션 풀 설정 |
| BE-1-04 | S3 + CloudFront 셋업 | AWS SDK v2 연동, 이미지 업로드 공통 모듈, 프리사인 URL 생성 | FileUploadService, S3Config |
| BE-1-05 | OAuth2 소셜 로그인 구현 | 카카오, 네이버, 구글, Apple 4종, JWT(RS256) 발급, Refresh Token Rotation | AuthController, OAuth2Service, JwtTokenProvider |
| BE-1-06 | 공통 응답 포맷 & 예외 처리 | `ApiResponse<T>` 통일 응답, `GlobalExceptionHandler`, 커스텀 에러 코드 체계 | ErrorCode enum, ApiResponse record |
| BE-1-07 | API 문서 자동화 | springdoc-openapi + Swagger UI, API 그룹핑 | `/api-docs` 엔드포인트 |
| BE-1-08 | CI/CD 파이프라인 | GitHub Actions: build → test → Docker build → ECR push | `.github/workflows/ci.yml` |
| BE-1-09 | Docker & K8s 기본 설정 | Dockerfile(멀티스테이지), docker-compose(로컬), EKS Deployment/Service YAML | `infra/` 디렉토리 |

#### FE (Frontend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FE-1-01 | Next.js 프로젝트 초기화 | Next.js 14 App Router + TypeScript + Tailwind CSS + ESLint/Prettier | 프로젝트 boilerplate |
| FE-1-02 | 디자인 시스템 토큰 반영 | tailwind.config.ts에 DS 토큰 반영(color, spacing, typography, shadow) | design-tokens.ts |
| FE-1-03 | 공통 컴포넌트 라이브러리 | shadcn/ui 기반 커스텀: Button, Input, Card, Modal, Toast, Badge, Spinner | `components/ui/` |
| FE-1-04 | 레이아웃 구조 | RootLayout, AuthLayout, MainLayout(Bottom Tab), Header/Footer | `app/layout.tsx`, `app/(main)/layout.tsx` |
| FE-1-05 | API Client 셋업 | axios 인스턴스, 인터셉터(JWT 자동 갱신), API 에러 핸들링, TanStack Query Provider | `lib/api/client.ts` |
| FE-1-06 | Auth Store 구현 | Zustand: user, token, login/logout, isAuthenticated | `stores/auth-store.ts` |
| FE-1-07 | CI 파이프라인 | GitHub Actions: lint → type-check → test → build | `.github/workflows/fe-ci.yml` |

#### FL (Flutter App)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FL-1-01 | Flutter 프로젝트 초기화 | Flutter 3.x + Dart 3, clean architecture 폴더 구조 | `presentation/`, `domain/`, `data/`, `core/` |
| FL-1-02 | 테마 & 디자인 시스템 | DS 토큰 → ThemeData 변환, AppColors, AppTypography, AppSpacing 클래스 | `core/theme/` |
| FL-1-03 | 공통 위젯 라이브러리 | AppButton, AppInput, AppCard, AppBottomSheet, AppToast, 로딩 인디케이터 | `presentation/widgets/` |
| FL-1-04 | 네비게이션 구조 | go_router: 스플래시 → 온보딩 → 로그인 → 메인(Bottom Nav 5탭) | `core/router/app_router.dart` |
| FL-1-05 | 네트워크 레이어 | Dio + Retrofit: BaseURL, 인터셉터(JWT 갱신), 에러 핸들링 | `data/remote/api_client.dart` |
| FL-1-06 | 로컬 스토리지 | Hive: 토큰 저장, 사용자 설정, 캐시 | `data/local/` |
| FL-1-07 | FCM 셋업 | Firebase 프로젝트 연동, FCM 토큰 수집, 포그라운드/백그라운드 알림 핸들러 | `core/push/` |

#### PB (Publisher)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| PB-1-01 | Tailwind 유틸리티 정리 | DS 토큰 기반 커스텀 유틸리티 클래스, 반응형 브레이크포인트 정의 | `globals.css`, `tailwind.config.ts` 확장 |
| PB-1-02 | 공통 컴포넌트 퍼블리싱 | DS의 Button/Input/Card/Modal/Toast를 Tailwind로 구현, 모든 상태 대응 | 컴포넌트별 스토리(Storybook) |
| PB-1-03 | 반응형 레이아웃 가이드 | Mobile(375px), Tablet(768px), Desktop(1280px) 기준 레이아웃 가이드 문서 | `docs/responsive-guide.md` |

---

### Sprint 2 (Week 3~4) — 인증 완성 & 온보딩 & 커플 연동

#### DS (Designer)

| Task ID | 작업 내용 | 산출물 | 비고 |
|---------|----------|--------|------|
| DS-2-01 | 프로필 설정 화면 디자인 | 닉네임, 프로필 사진, 결혼 상태, 결혼일, 지역 설정 | 단계별 프로그레스 바 |
| DS-2-02 | 커플 연동 화면 디자인 | 초대 코드 생성/공유 화면, 코드 입력 화면, 연동 완료 축하 화면 | 카카오톡/문자 공유 버튼 |
| DS-2-03 | 비즈니스 회원 가입 화면 디자인 | 사업자등록증 업로드, 업체 정보 입력, 심사 상태 화면 | 서류 촬영 가이드 UI |
| DS-2-04 | 결혼 준비 체크리스트 화면 디자인 | 타임라인 뷰, 카테고리 뷰, 항목 상세, 완료 토글, 담당자 지정 | Sprint 3에서 구현 예정 |
| DS-2-05 | 예산 관리 화면 디자인 | 카테고리별 예산 설정, 지출 기록, 양가 분담, 원형/막대 차트 | Sprint 3에서 구현 예정 |

#### BE (Backend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| BE-2-01 | 온보딩 API | `POST /api/v1/auth/onboarding` — 결혼 상태, 예정일, 지역, 관심사 저장 | OnboardingController |
| BE-2-02 | 프로필 관리 API | `GET/PUT /api/v1/users/me` — 프로필 조회/수정, 프로필 이미지 업로드 (S3) | UserController, UserService |
| BE-2-03 | 커플 연동 API | `POST /api/v1/auth/couple/invite` (6자리 코드 생성), `POST /api/v1/auth/couple/connect` (코드 입력 → 연동) | CoupleController, CoupleService |
| BE-2-04 | 커플 연동 해제 API | `DELETE /api/v1/auth/couple` — 연동 해제, 공유 데이터 분리 정책 | CoupleService |
| BE-2-05 | 비즈니스 회원 가입 API | `POST /api/v1/auth/business/register` — 사업자등록증 업로드, 업체 정보 저장, 심사 요청 | BusinessAuthController |
| BE-2-06 | 비즈니스 심사 관리 API (Admin) | `GET /api/v1/admin/business/pending`, `PATCH /api/v1/admin/business/{id}/approve` | AdminBusinessController |
| BE-2-07 | 알림 서비스 기반 구축 | Kafka Producer/Consumer 셋업, 알림 이벤트 정의, 푸시(FCM) + 인앱 알림 저장 | NotificationService, KafkaConfig |
| BE-2-08 | 회원 등급 시스템 | 등급 계산 로직(SPROUT→BLOOM→MATE→BEST), 활동 기록 카운팅 | GradeService, ActivityService |

#### FE (Frontend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FE-2-01 | 소셜 로그인 화면 구현 | 카카오/네이버/구글/Apple 로그인 버튼, OAuth 리다이렉트 처리, 토큰 저장 | `app/login/page.tsx` |
| FE-2-02 | 온보딩 플로우 구현 | 4단계 슬라이드: 환영 → 결혼 상태 선택 → 날짜/지역 설정 → 관심사 선택 | `app/onboarding/` |
| FE-2-03 | 프로필 설정 화면 | 닉네임, 프로필 사진 업로드(크롭), 지역 선택(시/도 → 시/군/구 셀렉트) | `app/(main)/my/profile/` |
| FE-2-04 | 커플 연동 화면 | 초대 코드 생성 + 클립보드 복사 + 카카오 공유, 코드 입력 + 연동 결과 | `app/(main)/my/couple/` |
| FE-2-05 | 홈 화면 (대시보드) | D-Day 위젯, 진행률 게이지, 오늘의 할 일(체크리스트 연동은 S3), 예산 요약(S3) | `app/(main)/home/` |
| FE-2-06 | 인증 미들웨어 | Next.js middleware로 미인증 시 로그인 리다이렉트, 토큰 만료 시 갱신 | `middleware.ts` |

#### FL (Flutter App)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FL-2-01 | 스플래시 화면 | 로고 애니메이션, 토큰 유효성 검사 → 자동 로그인 또는 로그인 화면 이동 | `splash_screen.dart` |
| FL-2-02 | 소셜 로그인 구현 | 카카오/네이버/구글/Apple SDK 연동, 토큰 수신 → API 호출 → Hive 저장 | `login_screen.dart` |
| FL-2-03 | 온보딩 플로우 구현 | PageView 4단계, 각 단계 입력 → API 전송, 스킵 기능 | `onboarding/` |
| FL-2-04 | 프로필 설정 구현 | 프로필 사진(image_picker + crop), 닉네임, 지역 선택 | `profile_edit_screen.dart` |
| FL-2-05 | 커플 연동 구현 | 초대 코드 생성, 카카오/문자 공유(share_plus), 코드 입력 → 연동 | `couple_connect/` |
| FL-2-06 | 홈 화면 (대시보드) 구현 | D-Day 카운트다운, 진행률 원형 게이지, 오늘의 할 일 위젯 | `home_screen.dart` |
| FL-2-07 | Bottom Navigation 구현 | 5탭 네비게이션, 각 탭 Nested Navigation, 알림 뱃지 | `main_screen.dart` |

#### PB (Publisher)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| PB-2-01 | 로그인/온보딩 페이지 퍼블리싱 | DS 시안 → HTML/Tailwind 변환, 애니메이션(슬라이드 전환) | 마크업 완료 파일 |
| PB-2-02 | 홈 대시보드 퍼블리싱 | D-Day 위젯, 진행률 게이지 CSS, 카드 레이아웃 | 반응형 대응 완료 |
| PB-2-03 | 프로필/커플 연동 퍼블리싱 | 프로필 편집 폼, 코드 입력 UI(OTP 스타일 6자리 입력) | 인터랙션 포함 |

---

## Phase 2: Wedding Prep Core (Sprint 3~4)

> **목표**: 결혼 준비 핵심 기능 (체크리스트, 예산, 일정, 업체 탐색) 완성

### Sprint 3 (Week 5~6) — 체크리스트 & 예산 관리

#### DS (Designer)

| Task ID | 작업 내용 | 산출물 | 비고 |
|---------|----------|--------|------|
| DS-3-01 | 일정 관리 (캘린더) 화면 디자인 | 월간/주간 캘린더 뷰, 일정 추가/편집 모달, 커플 공유 표시(색상 구분) | 양력/음력 전환 |
| DS-3-02 | 업체 탐색 리스트 화면 디자인 | 카테고리 탭, 업체 카드(대표사진/이름/평점/가격대/지역), 필터 바텀시트 | 6개 카테고리 아이콘 |
| DS-3-03 | 업체 상세 화면 디자인 | 포트폴리오 갤러리, 가격표, 리뷰 목록, 지도, 찜/비교/문의 CTA | 리뷰 별점 UI |
| DS-3-04 | 비교함 화면 디자인 | 테이블형 비교 뷰(최대 5개 업체), 항목별 비교(가격/평점/위치) | 좌우 스크롤 |
| DS-3-05 | 하객 관리 화면 디자인 | 하객 리스트(그룹별), 청첩장 발송 상태, 참석 여부, 축의금 입력 | Sprint 4에서 구현 |

#### BE (Backend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| BE-3-01 | 체크리스트 도메인 & API | `checklists`, `checklist_items` DDL. 결혼일 기반 자동 생성 로직(D-12M~D-Day 템플릿). CRUD API | ChecklistController, ChecklistTemplateService |
| BE-3-02 | 체크리스트 커플 동기화 | 커플 연동된 경우 체크리스트 공유, 담당자 지정(신랑/신부/함께), WebSocket으로 실시간 동기화 이벤트 | ChecklistSyncService |
| BE-3-03 | 예산 관리 도메인 & API | `budgets`, `budget_categories`, `expenses` DDL. 카테고리 생성/수정, 지출 CRUD, 양가 분담 비율 설정 | BudgetController, BudgetService |
| BE-3-04 | 예산 통계 API | 카테고리별 예산 대비 지출 비율, 양가 분담 현황, 총 예산/지출/잔여 요약 | BudgetStatisticsService |
| BE-3-05 | 축의금 관리 API | `gifts` 테이블. 하객별 축의금 기록, 합계 통계 | GiftController |
| BE-3-06 | 일정 관리 도메인 & API | `schedules` DDL. 일정 CRUD, 커플 공유, 반복 일정, 알림 시점 설정 | ScheduleController, ScheduleService |
| BE-3-07 | 일정 알림 스케줄러 | Spring Scheduler + Kafka: D-7/D-3/D-1/D-Day 알림 이벤트 발행 | ScheduleAlarmService |

#### FE (Frontend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FE-3-01 | 체크리스트 화면 구현 | 타임라인 뷰(시기별 그룹), 카테고리 탭, 항목 토글(완료/미완료), 커스텀 항목 추가/편집 | `app/(main)/wedding/checklist/` |
| FE-3-02 | 체크리스트 진행률 연동 | 홈 대시보드의 진행률 게이지와 체크리스트 완료 상태 실시간 연동 | 홈 위젯 업데이트 |
| FE-3-03 | 예산 관리 화면 구현 | 카테고리별 예산 설정 폼, 지출 등록(수동 입력), 양가 분담 슬라이더 | `app/(main)/wedding/budget/` |
| FE-3-04 | 예산 차트 구현 | Recharts: 원형 차트(카테고리 비율), 막대 차트(예산 vs 지출), 요약 카드 | BudgetChart 컴포넌트 |
| FE-3-05 | 일정 관리 화면 구현 | 캘린더 라이브러리(react-day-picker), 일정 추가/편집 모달, 커플 일정 색상 구분 | `app/(main)/wedding/schedule/` |

#### FL (Flutter App)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FL-3-01 | 체크리스트 화면 구현 | 타임라인 뷰(SliverList), 카테고리 탭(TabBar), 항목 체크 + 담당자 지정 | `checklist/` |
| FL-3-02 | 체크리스트 실시간 동기화 | WebSocket 연동, 커플 상대방 변경 시 실시간 UI 반영 | ChecklistSyncProvider |
| FL-3-03 | 예산 관리 화면 구현 | 카테고리별 예산 입력, 지출 등록 바텀시트, 양가 분담 설정 | `budget/` |
| FL-3-04 | 예산 차트 구현 | fl_chart: 원형 차트(카테고리), 막대 차트(예산 vs 지출) | BudgetChartWidget |
| FL-3-05 | 일정 관리 화면 구현 | table_calendar, 일정 추가/편집, 커플 일정 표시 | `schedule/` |

#### PB (Publisher)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| PB-3-01 | 체크리스트 페이지 퍼블리싱 | 타임라인 뷰 마크업, 체크 애니메이션, 프로그레스 바 | 인터랙션 CSS |
| PB-3-02 | 예산 관리 페이지 퍼블리싱 | 카테고리 카드, 지출 기록 폼, 분담 슬라이더 | 반응형 완료 |
| PB-3-03 | 캘린더 페이지 퍼블리싱 | 캘린더 그리드, 일정 카드, 모달 | 커플 색상 구분 |

---

### Sprint 4 (Week 7~8) — 업체 탐색 & 비교

#### DS (Designer)

| Task ID | 작업 내용 | 산출물 | 비고 |
|---------|----------|--------|------|
| DS-4-01 | 리뷰 작성 화면 디자인 | 별점 입력(5점), 텍스트 리뷰, 사진 첨부(최대 5장), 카테고리 태그 | 작성 가이드 포함 |
| DS-4-02 | SNS 피드 화면 디자인 | 카드형 피드(사진/캡션/좋아요/댓글), 피드 탭(전체/팔로잉/지역별), 작성 FAB | Sprint 5에서 구현 |
| DS-4-03 | 게시글 작성 화면 디자인 | 사진/동영상 선택, 캡션 입력, 해시태그, 위치 태그, 업체 태그 | Sprint 5에서 구현 |
| DS-4-04 | 게시글 상세 화면 디자인 | 사진 슬라이더, 좋아요/댓글/저장/공유, 댓글 목록, 대댓글 | Sprint 5에서 구현 |
| DS-4-05 | 사용자 프로필 페이지 디자인 | 프로필 헤더(사진/닉네임/팔로워/팔로잉), 게시글 그리드, 팔로우 버튼 | Sprint 5에서 구현 |

#### BE (Backend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| BE-4-01 | 업체(Vendor) 도메인 & API | `vendors`, `vendor_categories`, `vendor_images`, `vendor_prices` DDL. 업체 CRUD, 목록 조회(필터/정렬/페이징) | VendorController, VendorService |
| BE-4-02 | 업체 검색 (Elasticsearch) | 업체 데이터 ES 인덱싱, 카테고리/지역/가격/평점 복합 필터 검색, 자동완성 | VendorSearchService, ES Index 설계 |
| BE-4-03 | 리뷰 도메인 & API | `reviews`, `review_images` DDL. 리뷰 CRUD, 평점 계산(가중 평균), 업체별 리뷰 목록 | ReviewController, ReviewService |
| BE-4-04 | 찜하기 & 비교함 API | `favorites` DDL. 찜 토글, 비교함 추가/제거(최대 5), 비교 데이터 조회 | FavoriteController, CompareService |
| BE-4-05 | 하객 관리 API | `guests` DDL. 하객 CRUD, 그룹 분류(신랑측/신부측/공통), 참석 여부, 축의금 기록, 통계 | GuestController, GuestService |
| BE-4-06 | 이미지 리사이징 서비스 | S3 업로드 후 Lambda 트리거 → 썸네일(150px), 중간(600px), 원본 3종 생성 | ImageResizeService (Lambda) |

#### FE (Frontend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FE-4-01 | 업체 탐색 리스트 구현 | 카테고리 탭 6종, 업체 카드 리스트(무한 스크롤), 필터 바텀시트(지역/가격/평점) | `app/(main)/wedding/vendors/` |
| FE-4-02 | 업체 상세 화면 구현 | 포트폴리오 갤러리(Lightbox), 가격표, 리뷰 섹션, 카카오맵 연동, 찜/비교 CTA | `app/(main)/wedding/vendors/[id]/` |
| FE-4-03 | 비교함 화면 구현 | 테이블 형태 비교 뷰, 항목별 하이라이트(최저가 등), 비교 항목 제거 | `app/(main)/wedding/compare/` |
| FE-4-04 | 리뷰 작성 화면 구현 | 별점 입력(인터랙티브), 텍스트 에디터, 사진 업로드(프리뷰 + 순서 변경) | ReviewForm 컴포넌트 |
| FE-4-05 | 하객 관리 화면 구현 | 하객 리스트(그룹별 섹션), 추가/편집 모달, 참석 여부 토글, 축의금 입력, 통계 요약 | `app/(main)/wedding/guests/` |

#### FL (Flutter App)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FL-4-01 | 업체 탐색 리스트 구현 | 카테고리 탭, 업체 카드(cached_network_image), 필터 바텀시트, 무한 스크롤 | `vendors/vendor_list_screen.dart` |
| FL-4-02 | 업체 상세 화면 구현 | 사진 갤러리(photo_view), 가격표, 리뷰 목록, 카카오맵 플러그인, 찜/비교 | `vendors/vendor_detail_screen.dart` |
| FL-4-03 | 비교함 화면 구현 | 가로 스크롤 비교 테이블, 항목 제거, 업체 상세 연결 | `vendors/compare_screen.dart` |
| FL-4-04 | 리뷰 작성 구현 | 별점 입력 위젯, 텍스트 입력, 이미지 선택(image_picker), 순서 변경(Draggable) | `reviews/review_form_screen.dart` |
| FL-4-05 | 하객 관리 구현 | 하객 목록(그룹별), 추가/편집, 참석 상태, 축의금 입력, 통계 카드 | `guests/` |

#### PB (Publisher)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| PB-4-01 | 업체 탐색 리스트 퍼블리싱 | 카테고리 탭, 업체 카드, 필터 바텀시트 마크업 | 반응형 그리드 |
| PB-4-02 | 업체 상세 퍼블리싱 | 갤러리, 가격표 테이블, 리뷰 섹션, 지도 영역 | 스크롤 인터랙션 |
| PB-4-03 | 비교함 퍼블리싱 | 수평 스크롤 비교 테이블, 하이라이트 처리 | 테이블 반응형 |
| PB-4-04 | 하객 관리 퍼블리싱 | 리스트/모달/통계 카드 마크업 | 인쇄 스타일시트 포함 |

---

## Phase 3: SNS & Community (Sprint 5~6)

> **목표**: SNS 피드, 팔로우, 해시태그, 검색, DM 구현

### Sprint 5 (Week 9~10) — SNS 피드 & 팔로우

#### DS (Designer)

| Task ID | 작업 내용 | 산출물 | 비고 |
|---------|----------|--------|------|
| DS-5-01 | 검색/탐색 화면 디자인 | 검색 바, 인기 해시태그, 지역별 탐색 맵, 인기 게시글 그리드 | 탐색 탭 구조 |
| DS-5-02 | DM 화면 디자인 | 대화 목록, 채팅 화면(말풍선), 이미지 전송, 읽음 표시 | Sprint 6에서 구현 |
| DS-5-03 | 알림 센터 디자인 | 알림 목록(좋아요/댓글/팔로우/시스템), 읽음/미읽음 구분, 알림 클릭 → 대상 이동 | 타입별 아이콘 |
| DS-5-04 | 마켓 홈 화면 디자인 | 신상품(B2B) / 중고거래 탭, 카테고리 아이콘, 상품 카드 | Sprint 7에서 구현 |
| DS-5-05 | 중고거래 등록/상세 화면 디자인 | 등록 폼(사진/설명/카테고리/상태/가격/지역/거래방식), 상세 뷰 | Sprint 7에서 구현 |

#### BE (Backend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| BE-5-01 | 게시글(Post) 도메인 & API | `posts`, `post_media`, `post_hashtags` DDL. 게시글 CRUD, 이미지/동영상 업로드(S3), 지역 태그 필수 검증 | PostController, PostService |
| BE-5-02 | 미디어 업로드 처리 | 이미지 멀티 업로드(최대 10장, 각 10MB), 동영상(최대 100MB) 업로드, 비동기 트랜스코딩 | MediaUploadService |
| BE-5-03 | 해시태그 시스템 | `hashtags` DDL. 해시태그 파싱 & 저장, 자동완성(ES), 인기 해시태그 집계(Redis Sorted Set) | HashtagService |
| BE-5-04 | 좋아요/댓글 API | `likes`, `comments` DDL. 좋아요 토글(Redis Write-back), 댓글 CRUD, 대댓글(1depth) | LikeService, CommentService |
| BE-5-05 | 팔로우 API | `follows` DDL. 팔로우/언팔로우 토글, 팔로워/팔로잉 목록, 카운트 | FollowController, FollowService |
| BE-5-06 | 피드 조회 API | 전체 피드(시간순 + 관심사 가중치), 팔로잉 피드, 지역별 피드. 커서 기반 페이지네이션 | FeedController, FeedService |
| BE-5-07 | 게시글 검색 (ES) | 게시글 ES 인덱싱, 키워드/해시태그/사용자/지역 검색, 인기 게시글 정렬 | PostSearchService |
| BE-5-08 | 게시 권한 검증 | 일반 회원(USER)만 게시 가능, BUSINESS 회원 게시 시도 시 403 반환 | PostPermissionService |

#### FE (Frontend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FE-5-01 | SNS 피드 화면 구현 | 카드형 피드(무한 스크롤), 탭(전체/팔로잉/지역별), 사진 슬라이더, 좋아요/댓글/저장 | `app/(main)/community/` |
| FE-5-02 | 게시글 작성 화면 구현 | 사진 멀티 업로드(드래그 순서 변경), 캡션 에디터, 해시태그 자동완성, 지역 선택(필수), 업체 태그 | `app/(main)/community/create/` |
| FE-5-03 | 게시글 상세 화면 구현 | 사진 갤러리, 좋아요/댓글/저장/공유, 댓글 목록(대댓글 토글), 해시태그 링크 | `app/(main)/community/[id]/` |
| FE-5-04 | 사용자 프로필 페이지 구현 | 프로필 헤더, 게시글 그리드(3열), 팔로우 버튼, 팔로워/팔로잉 목록 모달 | `app/(main)/community/user/[id]/` |
| FE-5-05 | 검색/탐색 화면 구현 | 검색 바(키워드/해시태그/사용자), 인기 해시태그 칩, 인기 게시글 그리드 | `app/(main)/community/explore/` |
| FE-5-06 | 알림 센터 구현 | 알림 목록(TanStack Query 폴링 30초), 타입별 아이콘, 미읽음 배지, 클릭 → 대상 이동 | `app/(main)/notifications/` |

#### FL (Flutter App)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FL-5-01 | SNS 피드 화면 구현 | 카드형 피드(CustomScrollView), 탭(전체/팔로잉/지역별), 사진 PageView, 좋아요 애니메이션 | `community/feed_screen.dart` |
| FL-5-02 | 게시글 작성 구현 | 갤러리에서 멀티 선택(photo_manager), 순서 변경, 캡션, 해시태그 자동완성, 지역 태그 | `community/create_post_screen.dart` |
| FL-5-03 | 게시글 상세 구현 | 사진 슬라이더, 좋아요/댓글/저장/공유, 댓글 입력(키보드 어보이드), 대댓글 | `community/post_detail_screen.dart` |
| FL-5-04 | 프로필 & 팔로우 구현 | 프로필 페이지, 게시글 그리드(SliverGrid), 팔로우/언팔로우, 팔로워/팔로잉 리스트 | `community/user_profile_screen.dart` |
| FL-5-05 | 검색/탐색 구현 | 검색 바, 해시태그 칩, 검색 결과(게시글/사용자/해시태그 탭), 인기 게시글 | `community/explore_screen.dart` |
| FL-5-06 | 알림 센터 구현 | 알림 리스트, 타입별 아이콘, 미읽음 뱃지(Bottom Nav), 탭 → 상세 이동 | `notifications/notification_screen.dart` |

#### PB (Publisher)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| PB-5-01 | SNS 피드 퍼블리싱 | 카드형 피드 마크업, 사진 슬라이더, 좋아요/댓글 인터랙션 | 반응형 피드 |
| PB-5-02 | 게시글 작성 퍼블리싱 | 이미지 업로드 영역, 해시태그 자동완성 드롭다운, 지역 선택 | 모바일 UX 최적화 |
| PB-5-03 | 프로필 페이지 퍼블리싱 | 프로필 헤더, 3열 그리드, 팔로우 버튼 상태 | 스크롤 인터랙션 |
| PB-5-04 | 알림 센터 퍼블리싱 | 알림 리스트 항목, 미읽음 스타일, 스와이프 삭제 | 터치 인터랙션 |

---

### Sprint 6 (Week 11~12) — DM & 실시간 채팅

#### DS (Designer)

| Task ID | 작업 내용 | 산출물 | 비고 |
|---------|----------|--------|------|
| DS-6-01 | 중고거래 채팅 화면 디자인 | 거래용 채팅(상품 카드 상단 고정, 가격 제안 UI, 거래 상태 변경 버튼) | Sprint 8에서 구현 |
| DS-6-02 | 안전거래(에스크로) 플로우 디자인 | 결제 → 발송 → 수령 확인 단계별 UI, 운송장 입력, 분쟁 접수 | Sprint 8에서 구현 |
| DS-6-03 | B2B 마켓 상품 상세 디자인 | 상품 이미지 갤러리, 가격/옵션, 리뷰, 장바구니/구매 CTA | Sprint 7에서 구현 |
| DS-6-04 | 결혼 생활 대시보드 디자인 | 결혼 D+Day, 가족 행사 위젯, 신혼집 체크리스트 진행률 | Sprint 9에서 구현 |
| DS-6-05 | 가족 캘린더 화면 디자인 | 가족 구성원 등록, 행사 등록(음력/양력), 월간 캘린더, 알림 설정 | Sprint 9에서 구현 |

#### BE (Backend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| BE-6-01 | 채팅 도메인 설계 | `chat_rooms`, `chat_messages`, `chat_participants` DDL | ChatDomain |
| BE-6-02 | WebSocket 서버 구현 | STOMP over WebSocket, JWT 인증, 연결 관리, 하트비트 | WebSocketConfig, ChatWebSocketHandler |
| BE-6-03 | DM 채팅 API | 채팅방 생성(1:1), 메시지 전송(텍스트/이미지), 메시지 목록 조회(커서 페이징), 읽음 처리 | ChatController, ChatService |
| BE-6-04 | 채팅 메시지 저장 & 알림 | Kafka로 메시지 비동기 저장, 오프라인 유저에게 FCM 푸시 | ChatMessageConsumer |
| BE-6-05 | 온라인 상태 관리 | Redis: `online:{userId}` TTL 5분, WebSocket 연결/해제 이벤트로 갱신 | PresenceService |
| BE-6-06 | 게시글 저장(북마크) API | `bookmarks` DDL. 저장/해제 토글, 저장한 게시글 목록 조회 | BookmarkController |
| BE-6-07 | 게시글 공유 기능 | 딥링크 생성, 카카오톡/외부 공유 URL, OG 메타태그 | ShareService, OGMetaController |
| BE-6-08 | 콘텐츠 신고 API | `reports` DDL. 게시글/댓글/사용자 신고, 관리자 심사 큐 | ReportController, ReportService |

#### FE (Frontend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FE-6-01 | DM 대화 목록 구현 | 대화 목록(최근 메시지 미리보기, 미읽음 카운트, 시간), 새 대화 시작 | `app/(main)/messages/` |
| FE-6-02 | DM 채팅 화면 구현 | STOMP.js 연동, 말풍선(발신/수신), 이미지 전송, 읽음 표시, 스크롤 투 바텀 | `app/(main)/messages/[roomId]/` |
| FE-6-03 | 게시글 저장/공유 기능 | 저장 버튼 토글, 공유 바텀시트(링크 복사, 카카오, 외부 앱), MY > 저장한 게시글 | BookmarkButton, ShareSheet |
| FE-6-04 | 신고 기능 구현 | 신고 사유 선택 모달(6종), 신고 완료 토스트, 차단 기능 | ReportModal 컴포넌트 |
| FE-6-05 | MY 페이지 구현 | 프로필 요약, 활동 내역(게시글/댓글/리뷰), 등급 & 포인트, 설정 | `app/(main)/my/` |

#### FL (Flutter App)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FL-6-01 | DM 대화 목록 구현 | 대화 목록 화면, 미리보기, 미읽음 뱃지, 새 대화 시작 | `chat/chat_list_screen.dart` |
| FL-6-02 | DM 채팅 화면 구현 | STOMP 연동, 말풍선 UI, 이미지 전송(image_picker), 읽음 처리, 키보드 어보이드 | `chat/chat_room_screen.dart` |
| FL-6-03 | 게시글 저장/공유 구현 | 저장 토글(애니메이션), 공유(share_plus), 딥링크 핸들링 | PostActions 위젯 |
| FL-6-04 | 신고/차단 구현 | 신고 사유 선택 바텀시트, 차단 확인 다이얼로그 | ReportSheet, BlockDialog |
| FL-6-05 | MY 페이지 구현 | 프로필 카드, 메뉴 리스트(게시글/리뷰/거래내역/설정), 등급 프로그레스 | `my/my_screen.dart` |

#### PB (Publisher)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| PB-6-01 | DM 채팅 퍼블리싱 | 대화 목록, 채팅 말풍선, 이미지 메시지, 타이핑 인디케이터 | 채팅 UI 마크업 |
| PB-6-02 | MY 페이지 퍼블리싱 | 프로필 카드, 메뉴 리스트, 등급 프로그레스 바 | 반응형 완료 |
| PB-6-03 | 신고/차단 모달 퍼블리싱 | 모달/바텀시트, 라디오 선택, 확인 버튼 | 접근성 대응 |

---

## Phase 4: Marketplace & SecondHand (Sprint 7~8)

> **목표**: B2B 마켓플레이스 + 중고거래 + 결제(에스크로) 완성

### Sprint 7 (Week 13~14) — B2B 마켓 & 중고거래 기본

#### DS (Designer)

| Task ID | 작업 내용 | 산출물 | 비고 |
|---------|----------|--------|------|
| DS-7-01 | 장바구니/주문/결제 화면 디자인 | 장바구니 목록, 주문 정보 입력, 결제 수단 선택, 주문 완료 | PG 연동 UI |
| DS-7-02 | 주문/배송 관리 화면 디자인 | 주문 내역 목록, 주문 상세(상태 타임라인), 배송 추적 | 상태별 아이콘 |
| DS-7-03 | 비즈니스 센터 화면 디자인 | 상품 등록/관리, 주문 관리, 매출 대시보드, 리뷰 관리 | 데스크탑 우선 |
| DS-7-04 | 결혼 생활 — 신혼집 체크리스트 화면 디자인 | 이사/가전/가구/인테리어 카테고리별 체크리스트, 참고 사진 | Sprint 9 구현 |

#### BE (Backend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| BE-7-01 | 상품(Product) 도메인 & API | `products`, `product_images`, `product_options` DDL. 상품 CRUD, 카테고리/검색 | ProductController, ProductService |
| BE-7-02 | 상품 검색 (ES) | 상품 ES 인덱싱, 카테고리/가격/지역 필터, 텍스트 검색, 인기순/최신순/가격순 정렬 | ProductSearchService |
| BE-7-03 | 장바구니 API | `cart_items` DDL. 장바구니 추가/수정/삭제, 수량 변경, 합계 계산 | CartController, CartService |
| BE-7-04 | 주문 도메인 & API | `orders`, `order_items` DDL. 주문 생성, 상태 관리(결제대기→결제완료→배송중→배송완료→구매확정), 주문 내역 조회 | OrderController, OrderService |
| BE-7-05 | 중고거래 도메인 & API | `secondhand_items`, `secondhand_images` DDL. 상품 CRUD, 카테고리/상태/지역 필터, 검색(ES) | SecondHandController, SecondHandService |
| BE-7-06 | 중고거래 채팅 연동 | 중고 상품별 채팅방 자동 생성, 상품 카드 표시, 거래 상태 연동 | SecondHandChatService |
| BE-7-07 | 위시리스트 API | `wishlists` DDL. 위시리스트 추가/삭제, 목록 조회 | WishlistController |

#### FE (Frontend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FE-7-01 | 마켓 홈 구현 | 신상품/중고거래 탭, 카테고리 아이콘 네비게이션, 추천 상품 섹션 | `app/(main)/market/` |
| FE-7-02 | 신상품 리스트/상세 구현 | 상품 그리드(무한 스크롤), 필터(카테고리/가격), 상품 상세(갤러리/옵션/리뷰/장바구니) | `app/(main)/market/products/` |
| FE-7-03 | 중고거래 리스트 구현 | 상품 카드 리스트, 필터(카테고리/지역/가격/상태), 정렬, 무한 스크롤 | `app/(main)/market/secondhand/` |
| FE-7-04 | 중고거래 등록 화면 구현 | 사진 업로드(최대 10장), 제목/설명, 카테고리/상태 선택, 가격 입력, 지역/거래방식 선택 | `app/(main)/market/secondhand/create/` |
| FE-7-05 | 중고거래 상세 화면 구현 | 사진 갤러리, 판매자 정보(매너 온도), 채팅하기/가격 제안 CTA, 찜하기 | `app/(main)/market/secondhand/[id]/` |
| FE-7-06 | 장바구니 화면 구현 | 상품 목록, 수량 변경, 옵션 변경, 합계, 주문하기 버튼 | `app/(main)/market/cart/` |

#### FL (Flutter App)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FL-7-01 | 마켓 홈 구현 | 신상품/중고 탭, 카테고리 가로 스크롤, 추천 상품 | `market/market_home_screen.dart` |
| FL-7-02 | 신상품 리스트/상세 구현 | 상품 그리드, 필터, 상품 상세(갤러리/옵션/리뷰/장바구니) | `market/product_*` |
| FL-7-03 | 중고거래 리스트/등록 구현 | 상품 카드 리스트, 필터 바텀시트, 등록 폼(이미지 선택, 카테고리/상태/가격/지역) | `market/secondhand_*` |
| FL-7-04 | 중고거래 상세 구현 | 사진 갤러리, 판매자 프로필, 채팅/가격 제안 버튼, 찜하기 | `market/secondhand_detail_screen.dart` |
| FL-7-05 | 장바구니 구현 | 상품 리스트, 수량 조절, 스와이프 삭제, 합계, 주문하기 | `market/cart_screen.dart` |

#### PB (Publisher)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| PB-7-01 | 마켓 홈/리스트 퍼블리싱 | 카테고리 네비, 상품 그리드, 중고 카드 | 반응형 그리드 |
| PB-7-02 | 상품 상세 퍼블리싱 | 갤러리, 옵션 선택, 리뷰 섹션, CTA 고정 바텀 | 스크롤 인터랙션 |
| PB-7-03 | 중고거래 등록 퍼블리싱 | 이미지 업로드 영역, 폼 레이아웃, 상태 선택 칩 | 모바일 최적화 |
| PB-7-04 | 장바구니 퍼블리싱 | 상품 행, 수량 조절, 합계 바 | 체크박스 인터랙션 |

---

### Sprint 8 (Week 15~16) — 결제 & 안전거래

#### DS (Designer)

| Task ID | 작업 내용 | 산출물 | 비고 |
|---------|----------|--------|------|
| DS-8-01 | 구독 결제 화면 디자인 | 플랜 비교(Free/Basic), 결제 화면, 구독 관리 | 가격 카드 UI |
| DS-8-02 | 거래 후기 화면 디자인 | 매너 평가(항목별), 텍스트 후기, 매너 온도 표시 | 이모지 기반 |
| DS-8-03 | 설정 화면 디자인 | 알림 설정(토글별), 개인정보, 차단 관리, 앱 설정, 계정 관리 | Sprint 9 보완 |

#### BE (Backend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| BE-8-01 | PG 결제 연동 | 토스페이먼츠/NHN KCP 연동, 결제 요청/승인/취소 API, 웹훅 처리 | PaymentController, PaymentService |
| BE-8-02 | 안전거래(에스크로) 서비스 | `escrow_transactions` DDL. 구매자 결제 → 금액 보관 → 판매자 발송(운송장 등록) → 수령 확인 → 정산 | EscrowController, EscrowService |
| BE-8-03 | 에스크로 자동 확인 스케줄러 | 수령 미확인 72시간 후 자동 확인 처리 → 판매자 정산 | EscrowAutoConfirmScheduler |
| BE-8-04 | 정산 서비스 | `settlements` DDL. 에스크로 정산(수수료 3.5% 차감), B2B 주문 정산(수수료 5~10%), 정산 내역 | SettlementService |
| BE-8-05 | 가격 제안 API | `price_offers` DDL. 구매자 가격 제안, 판매자 수락/거절, 알림 | PriceOfferController |
| BE-8-06 | 거래 후기 & 매너 온도 | `trade_reviews` DDL. 상호 평가(항목별), 매너 온도 계산(초기 36.5°, ±0.1~0.5) | TradeReviewService, MannerService |
| BE-8-07 | 비즈니스 센터 API | 상품 등록/수정/삭제, 주문 관리(상태 변경), 매출 통계(일/주/월), 리뷰 응답 | BusinessCenterController |
| BE-8-08 | 구독 서비스 | `subscriptions` DDL. Free/Basic 플랜, 결제 연동, 자동 갱신, 해지 | SubscriptionController, SubscriptionService |

#### FE (Frontend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FE-8-01 | 주문/결제 화면 구현 | 주문 정보 입력(배송지), 결제 수단 선택(토스 SDK), 결제 진행, 결과 화면 | `app/(main)/market/order/` |
| FE-8-02 | 안전거래 플로우 구현 | 구매자: 안전거래 결제 → 수령 확인. 판매자: 운송장 등록 → 정산 확인. 단계별 상태 표시 | `app/(main)/market/escrow/` |
| FE-8-03 | 주문/배송 관리 구현 | 주문 내역 리스트, 주문 상세(상태 타임라인), 배송 추적 링크 | `app/(main)/my/orders/` |
| FE-8-04 | 가격 제안 기능 구현 | 가격 제안 모달, 제안 내역, 판매자 측 수락/거절 UI | PriceOfferModal |
| FE-8-05 | 거래 후기 작성 구현 | 매너 평가 항목 선택, 텍스트 후기, 매너 온도 표시 | TradeReviewForm |
| FE-8-06 | 비즈니스 센터 (웹) | 상품 관리(CRUD), 주문 관리(상태 변경), 매출 대시보드(차트), 리뷰 관리 | `app/business/` (별도 레이아웃) |
| FE-8-07 | 구독 결제 화면 구현 | 플랜 비교 카드, 결제 화면, 구독 관리(MY), 해지 플로우 | `app/(main)/my/subscription/` |

#### FL (Flutter App)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FL-8-01 | 주문/결제 구현 | 주문 정보 입력, 토스/PG SDK 연동, 결제 결과 화면 | `market/order_screen.dart` |
| FL-8-02 | 안전거래 플로우 구현 | 구매자: 결제 → 수령 확인. 판매자: 운송장 입력 → 정산. 상태 스텝 인디케이터 | `market/escrow_screen.dart` |
| FL-8-03 | 주문 내역 구현 | 주문 목록, 상세(타임라인), 배송 추적 | `my/orders_screen.dart` |
| FL-8-04 | 중고거래 채팅 구현 | 상품 카드 상단 고정, 가격 제안 버튼, 거래 상태 변경 | `chat/trade_chat_screen.dart` |
| FL-8-05 | 거래 후기 구현 | 매너 평가 항목, 후기 작성, 매너 온도 프로그레스 | `market/trade_review_screen.dart` |
| FL-8-06 | 구독 관리 구현 | 플랜 카드, 인앱 결제(in_app_purchase), 구독 상태 관리 | `my/subscription_screen.dart` |

#### PB (Publisher)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| PB-8-01 | 결제/주문 퍼블리싱 | 주문 폼, 결제 수단 선택, 결과 화면 | 모바일 결제 UX |
| PB-8-02 | 안전거래 퍼블리싱 | 단계별 스텝 인디케이터, 운송장 입력, 확인 버튼 | 상태별 스타일 |
| PB-8-03 | 비즈니스 센터 퍼블리싱 | 상품 등록 폼, 주문 테이블, 매출 차트 영역, 리뷰 응답 | 데스크탑 레이아웃 |
| PB-8-04 | 구독 카드 퍼블리싱 | 플랜 비교 카드, CTA 버튼, 현재 플랜 표시 | 인터랙티브 카드 |

---

## Phase 5: Married Life & Polish (Sprint 9~10)

> **목표**: 결혼 생활 기능 + 전체 QA + 성능 최적화

### Sprint 9 (Week 17~18) — 결혼 생활 기능

#### DS (Designer)

| Task ID | 작업 내용 | 산출물 | 비고 |
|---------|----------|--------|------|
| DS-9-01 | 전체 UI 리뷰 & 일관성 점검 | 디자인 QA 리포트 | 전 화면 스크린샷 비교 |
| DS-9-02 | 빈 상태(Empty State) 디자인 | 각 화면의 데이터 없음 상태 일러스트 + 안내 텍스트 | 최소 15개 화면 |
| DS-9-03 | 에러 상태 화면 디자인 | 네트워크 오류, 서버 오류, 404, 권한 없음 | 재시도 CTA 포함 |
| DS-9-04 | 로딩 상태 디자인 | 스켈레톤 UI(피드/리스트/카드), 풀스크린 로딩 | 각 화면별 스켈레톤 |
| DS-9-05 | 앱 스토어 에셋 | 앱 아이콘(1024x1024), 스크린샷(6.7"/6.1"/12.9"), 프로모션 배너 | iOS + Android |

#### BE (Backend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| BE-9-01 | 가족 구성원 도메인 & API | `family_members` DDL. 구성원 등록(이름/관계/생일-음력or양력), 수정, 삭제 | FamilyMemberController |
| BE-9-02 | 가족 행사 도메인 & API | `family_events` DDL. 행사 CRUD, 음력↔양력 변환 라이브러리 연동, 매년 자동 갱신 | FamilyEventController, LunarCalendarService |
| BE-9-03 | 가족 행사 알림 스케줄러 | D-7/D-3/D-1/D-Day 푸시 알림, 커스텀 알림 시점 | FamilyEventAlarmService |
| BE-9-04 | 신혼집 체크리스트 API | 이사/가전/가구/인테리어 카테고리별 체크리스트 템플릿, CRUD, 커플 공유 | NewHomeChecklistService |
| BE-9-05 | Admin 대시보드 API | 전체 사용자/게시글/거래/매출 통계, 업체 심사 큐, 신고 관리, 콘텐츠 모더레이션 큐 | AdminDashboardController |
| BE-9-06 | 성능 최적화 | DB 쿼리 최적화(N+1 제거, 인덱스 추가), Redis 캐시 전략 강화, API 응답 시간 점검 | 성능 리포트 |
| BE-9-07 | 보안 점검 | JWT 만료/갱신 검증, Rate Limiting 설정 확인, SQL Injection/XSS 점검, 입력 검증 강화 | 보안 체크리스트 |

#### FE (Frontend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FE-9-01 | 결혼 생활 대시보드 구현 | 결혼 D+Day, 다가오는 가족 행사 위젯, 신혼집 체크리스트 진행률 | `app/(main)/life/` |
| FE-9-02 | 가족 캘린더 구현 | 가족 구성원 등록/편집, 행사 등록(음력/양력 전환), 월간 캘린더, 알림 설정 | `app/(main)/life/family/` |
| FE-9-03 | 신혼집 체크리스트 구현 | 카테고리별 체크리스트(이사/가전/가구/인테리어), 항목 체크, 커플 공유 | `app/(main)/life/newhome/` |
| FE-9-04 | 전체 UI 일관성 점검 | 빈 상태 적용, 에러 상태 적용, 스켈레톤 로딩 적용, 토스트 메시지 통일 | 전 화면 점검 |
| FE-9-05 | 성능 최적화 | 이미지 lazy loading, 번들 사이즈 최적화, React.memo/useMemo 적용, Lighthouse 점수 90+ | 성능 리포트 |
| FE-9-06 | 접근성 점검 | aria-label, 키보드 네비게이션, 색상 대비, 스크린 리더 테스트 | a11y 체크리스트 |

#### FL (Flutter App)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FL-9-01 | 결혼 생활 대시보드 구현 | D+Day 위젯, 가족 행사 카드, 신혼집 진행률 | `life/life_home_screen.dart` |
| FL-9-02 | 가족 캘린더 구현 | 가족 구성원 관리, 행사 등록/편집, 캘린더 뷰, 알림 설정 | `life/family_calendar_screen.dart` |
| FL-9-03 | 신혼집 체크리스트 구현 | 카테고리별 체크리스트, 항목 체크, 커플 동기화 | `life/newhome_checklist_screen.dart` |
| FL-9-04 | 전체 UI 일관성 점검 | 빈 상태, 에러 상태, 스켈레톤(Shimmer), 스낵바 통일 | 전 화면 점검 |
| FL-9-05 | 성능 최적화 | 이미지 캐시, 리스트 성능(ListView.builder), 메모리 누수 점검, 앱 시작 시간 최적화 | 성능 리포트 |
| FL-9-06 | 딥링크 & 유니버설 링크 | 게시글/상품/중고거래 딥링크, iOS Universal Link / Android App Link | DeepLinkHandler |

#### PB (Publisher)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| PB-9-01 | 결혼 생활 화면 퍼블리싱 | 대시보드, 가족 캘린더, 신혼집 체크리스트 | 반응형 완료 |
| PB-9-02 | 빈 상태/에러 상태 퍼블리싱 | 전체 화면 빈 상태 & 에러 상태 마크업 | 일러스트 + CTA |
| PB-9-03 | 스켈레톤 UI 퍼블리싱 | 피드/리스트/카드별 스켈레톤 애니메이션 | CSS 애니메이션 |

---

### Sprint 10 (Week 19~20) — 통합 QA & Launch Prep

#### DS (Designer)

| Task ID | 작업 내용 | 산출물 | 비고 |
|---------|----------|--------|------|
| DS-10-01 | 디자인 QA (최종) | 전 화면 디자인 시안 대비 구현물 비교 리포트, 수정 요청 목록 | 스크린샷 비교 |
| DS-10-02 | 마케팅 에셋 제작 | 랜딩 페이지 히어로, SNS 광고 이미지, 앱 스토어 프로모션 텍스트 | 런칭 마케팅용 |
| DS-10-03 | 최종 디자인 시스템 문서화 | Figma 컴포넌트 라이브러리 정리, 사용 가이드 문서 | 유지보수용 |

#### BE (Backend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| BE-10-01 | 통합 테스트 작성 | 주요 플로우 통합 테스트: 회원가입→온보딩→커플연동, 게시글 작성→좋아요/댓글, 중고등록→채팅→안전거래 | IntegrationTest |
| BE-10-02 | 부하 테스트 | k6/JMeter: 동시 접속 1,000명, 피드 조회 TPS 500+, 채팅 동시 500 세션 | 부하 테스트 리포트 |
| BE-10-03 | DB 마이그레이션 최종 점검 | 전체 Flyway 마이그레이션 순서 검증, 인덱스 최적화, 불필요 컬럼 정리 | DB 스키마 최종본 |
| BE-10-04 | 프로덕션 인프라 셋업 | EKS 프로덕션 클러스터, RDS(Multi-AZ), ElastiCache, ES 프로덕션, Kafka 클러스터 | 인프라 구성도 |
| BE-10-05 | 모니터링 & 로깅 셋업 | Grafana + Prometheus: API 응답 시간, 에러율, JVM 메트릭. ELK: 로그 수집/검색 | 모니터링 대시보드 |
| BE-10-06 | 운영 도구 | Admin 페이지 배포, 배치 스케줄러 점검, 백업 정책 확인 | 운영 매뉴얼 |
| BE-10-07 | API 문서 최종 정리 | OpenAPI 스펙 전체 검증, 요청/응답 예시 완성 | Swagger 최종본 |

#### FE (Frontend)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FE-10-01 | E2E 테스트 작성 | Playwright: 회원가입, 온보딩, 체크리스트, 피드 게시, 중고 등록, 결제 플로우 | `e2e/` 테스트 |
| FE-10-02 | 크로스 브라우저 테스트 | Chrome, Safari, Samsung Internet, Firefox에서 주요 플로우 검증 | 호환성 리포트 |
| FE-10-03 | SEO 최적화 | 메타태그, OG태그, sitemap.xml, robots.txt, Next.js generateMetadata | SEO 체크리스트 |
| FE-10-04 | PWA 설정 | manifest.json, Service Worker(오프라인 캐시), 앱 설치 배너 | PWA 설정 |
| FE-10-05 | 버그 수정 & 디자인 QA 반영 | DS의 디자인 QA 리포트 기반 UI 수정 | 수정 완료 목록 |
| FE-10-06 | 프로덕션 배포 준비 | Vercel/AWS 프로덕션 환경 설정, 환경 변수, 도메인 연결 | 배포 체크리스트 |

#### FL (Flutter App)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| FL-10-01 | 통합 테스트 | integration_test: 회원가입→온보딩, 체크리스트, 피드 게시, 중고 거래 플로우 | `integration_test/` |
| FL-10-02 | iOS 심사 준비 | App Store Connect 메타데이터, 스크린샷, 개인정보처리방침 URL, 앱 설명 | 심사 제출 |
| FL-10-03 | Android 출시 준비 | Google Play Console 메타데이터, 스크린샷, 앱 서명 키, 출시 트랙(내부→프로덕션) | 출시 설정 |
| FL-10-04 | 크래시 모니터링 | Firebase Crashlytics 연동, 비치명적 오류 로깅 | 크래시 대시보드 |
| FL-10-05 | 앱 성능 프로파일링 | Flutter DevTools: 렌더링 성능(60fps), 메모리, 앱 사이즈 최적화 | 성능 리포트 |
| FL-10-06 | 버그 수정 & 디자인 QA 반영 | DS 디자인 QA 리포트 기반 UI 수정, 기기별 호환성(iPhone SE~16 Pro Max, 다양한 Android) | 수정 완료 목록 |

#### PB (Publisher)

| Task ID | 작업 내용 | 상세 | 산출물 |
|---------|----------|------|--------|
| PB-10-01 | 전체 마크업 QA | 전 화면 마크업 검수, 크로스 브라우저 확인, 반응형 확인 | QA 리포트 |
| PB-10-02 | 접근성 최종 점검 | WCAG 2.1 AA 기준, 키보드 네비게이션, 스크린 리더, 색상 대비 | 접근성 리포트 |
| PB-10-03 | 랜딩 페이지 퍼블리싱 | 서비스 소개 랜딩 페이지, 반응형, 앱 다운로드 CTA | 런칭 페이지 |

---

## 버퍼 기간: QA & Launch Prep (Week 21~22)

> **이 기간은 스프린트 10에서 넘어온 미완료 작업, 긴급 버그 수정, 최종 런칭 준비에 사용**

| 항목 | 작업 내용 | 담당 |
|------|----------|------|
| CBT (클로즈드 베타) | 내부 팀 + 초대 사용자 50명 테스트 | 전체 |
| 크리티컬 버그 수정 | CBT 피드백 기반 P0/P1 버그 수정 | BE + FE + FL |
| 앱 스토어 심사 | iOS App Store / Google Play Store 심사 제출 | FL |
| 프로덕션 배포 | 웹 + 앱 + 서버 프로덕션 배포 | BE + FE + FL |
| 모니터링 확인 | 프로덕션 환경 모니터링 대시보드 점검 | BE |
| 런칭 마케팅 | 랜딩 페이지 오픈, SNS 사전 마케팅 | DS + PB |

---

## 마일스톤 요약

| 마일스톤 | 완료 시점 | 주요 산출물 | 검증 기준 |
|---------|----------|-----------|----------|
| **M1: Auth & Setup** | Sprint 2 완료 (W4) | 소셜 로그인 4종, 온보딩, 커플 연동, 인프라 | 로그인 → 온보딩 → 커플 연동 E2E 통과 |
| **M2: Wedding Prep** | Sprint 4 완료 (W8) | 체크리스트, 예산, 일정, 업체 탐색, 리뷰, 하객 | 체크리스트 생성/공유, 업체 비교 동작 |
| **M3: SNS & Chat** | Sprint 6 완료 (W12) | 피드, 좋아요/댓글, 팔로우, 검색, DM 채팅 | 게시글 작성→피드 노출→좋아요/댓글→DM E2E |
| **M4: Commerce** | Sprint 8 완료 (W16) | B2B 마켓, 중고거래, 결제, 안전거래, 구독 | 상품 구매 결제, 중고거래 에스크로 E2E |
| **M5: Launch Ready** | Sprint 10 + 버퍼 (W22) | 결혼 생활, 전체 QA, 앱 심사, 프로덕션 배포 | 부하 테스트 통과, 앱 스토어 심사 승인 |

---

## 리스크 관리

| 리스크 | 영향 | 대응 전략 | 모니터링 시점 |
|--------|------|----------|-------------|
| **PG 결제 연동 지연** | 높음 | Sprint 7 초반에 PG 계약 시작, 테스트 환경 조기 셋업 | Sprint 6 중간 |
| **앱 스토어 심사 리젝** | 높음 | 심사 가이드라인 사전 체크리스트 작성, 버퍼 기간 활용 | Sprint 9 |
| **ES/Kafka 운영 복잡도** | 중간 | AWS Managed Service 활용(OpenSearch, MSK), DevOps 전담 | Sprint 1 결정 |
| **실시간 채팅 안정성** | 중간 | WebSocket 연결 풀 관리, 재접속 로직, 메시지 유실 방지(Kafka) | Sprint 6 |
| **디자인 지연으로 개발 블로킹** | 높음 | 디자인은 항상 1~2 스프린트 선행, 와이어프레임 우선 공유 | 매 스프린트 |
| **5개월 일정 부족** | 높음 | MVP 범위 엄격 관리, Post-MVP 기능 과감히 제외, 버퍼 기간 활용 | 매 스프린트 회고 |

### 리스크 완화 원칙

1. **디자인 선행**: DS는 항상 개발팀보다 1 스프린트 앞서 디자인을 완료한다
2. **API 우선**: BE는 API 명세를 스프린트 첫 2일 내에 확정하여 FE/FL 병렬 작업 가능하게 한다
3. **MVP 고수**: 스프린트 중 범위 추가(Scope Creep) 발생 시 PM이 즉시 판단하여 Post-MVP로 이동
4. **데일리 싱크**: 매일 15분 스탠드업으로 블로커 조기 발견

---

## Definition of Done

### 코드 수준

- [ ] 기능이 요구사항을 충족한다
- [ ] 단위 테스트 커버리지 80% 이상
- [ ] 코드 리뷰 승인 (최소 1명)
- [ ] lint / type-check 통과
- [ ] 보안 취약점 없음 (OWASP Top 10)

### 스프린트 수준

- [ ] 모든 P0 작업 완료
- [ ] P1 작업 80% 이상 완료
- [ ] 디자인 QA 주요 이슈 해결
- [ ] API 문서 업데이트
- [ ] 스프린트 회고 실시

### 릴리즈 수준

- [ ] 전체 E2E 테스트 통과
- [ ] 부하 테스트 목표 달성 (P99 < 500ms)
- [ ] 크로스 브라우저/기기 호환성 확인
- [ ] 앱 스토어 심사 제출/승인
- [ ] 모니터링 & 알림 설정 완료
- [ ] 운영 매뉴얼 작성

---

## 부록: 스프린트별 업무량 요약

| Sprint | BE (Task수) | FE (Task수) | FL (Task수) | DS (Task수) | PB (Task수) |
|--------|------------|------------|------------|------------|------------|
| S1 | 9 | 7 | 7 | 7 | 3 |
| S2 | 8 | 6 | 7 | 5 | 3 |
| S3 | 7 | 5 | 5 | 5 | 3 |
| S4 | 6 | 5 | 5 | 5 | 4 |
| S5 | 8 | 6 | 6 | 5 | 4 |
| S6 | 8 | 5 | 5 | 5 | 3 |
| S7 | 7 | 6 | 5 | 4 | 4 |
| S8 | 8 | 7 | 6 | 3 | 4 |
| S9 | 7 | 6 | 6 | 5 | 3 |
| S10 | 7 | 6 | 6 | 3 | 3 |
| **합계** | **75** | **59** | **58** | **47** | **34** |

---

*이 스프린트 계획은 PRD v1.0 기반으로 작성되었으며, 각 스프린트 회고 시 우선순위와 범위를 조정합니다.*
*Post-MVP 기능(스토리, AI 추천, 가계부, 임신/육아 상세, 그룹채팅, 광고 플랫폼 등)은 런칭 후 별도 로드맵으로 관리합니다.*
