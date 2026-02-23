# Sprint 3 진행 현황

## S3-1 완료 ✅ (DB/도메인 뼈대)

### Flyway
- `backend/domain/src/main/resources/db/migration/V2__init_sprint3_wedding_prep.sql`
  - checklists, checklist_items
  - budgets, budget_categories, expenses
  - schedules
  - 인덱스 포함

### Domain Entity/Repository
- Checklist
  - `Checklist`, `ChecklistItem`, `ChecklistStatus`, `ChecklistAssignee`
  - `ChecklistRepository`, `ChecklistItemRepository`
- Budget
  - `Budget`, `BudgetCategory`, `Expense`, `PayerType`
  - `BudgetRepository`, `BudgetCategoryRepository`, `ExpenseRepository`
- Schedule
  - `Schedule`, `ScheduleStatus`
  - `ScheduleRepository`

### 검증
- `./gradlew :domain:compileJava :api:compileJava` ✅

## S3-2 완료 ✅ (체크리스트 API)

### API
- `POST /api/v1/checklists`
- `GET /api/v1/checklists`
- `PUT /api/v1/checklists/{checklistId}`
- `DELETE /api/v1/checklists/{checklistId}`
- `POST /api/v1/checklists/{checklistId}/items`
- `PUT /api/v1/checklists/{checklistId}/items/{itemId}`
- `PATCH /api/v1/checklists/{checklistId}/items/{itemId}/toggle`
- `DELETE /api/v1/checklists/{checklistId}/items/{itemId}`

### 구현 포인트
- 체크리스트/아이템 CRUD
- 아이템 완료 토글(`completed`, `completedAt`)
- 목록 조회 시 체크리스트별 진행률(`progressPercent`) 계산
- 체크리스트 관련 에러코드 추가
  - `CL001 CHECKLIST_NOT_FOUND`
  - `CL002 CHECKLIST_ITEM_NOT_FOUND`

### 검증
- `./gradlew :api:compileJava` ✅

## S3-3 완료 ✅ (예산 API)

### API
- `PUT /api/v1/budgets/me` (예산 생성/수정)
- `GET /api/v1/budgets/me` (예산 요약)
- `POST /api/v1/budgets/categories`
- `PUT /api/v1/budgets/categories/{categoryId}`
- `DELETE /api/v1/budgets/categories/{categoryId}`
- `POST /api/v1/budgets/expenses`
- `GET /api/v1/budgets/expenses?from&to`
- `PUT /api/v1/budgets/expenses/{expenseId}`
- `DELETE /api/v1/budgets/expenses/{expenseId}`

### 구현 포인트
- 카테고리/지출 CRUD
- 지출 등록·수정·삭제 시 카테고리 `spentAmount` 동기화
- 예산 요약 응답(`totalBudget/totalPlanned/totalSpent/totalRemaining`) 제공
- 예산 관련 에러코드 추가
  - `BG001 BUDGET_NOT_FOUND`
  - `BG002 BUDGET_CATEGORY_NOT_FOUND`
  - `BG003 EXPENSE_NOT_FOUND`

### 검증
- `./gradlew :api:compileJava` ✅

## S3-4 완료 ✅ (일정 API)

### API
- `POST /api/v1/schedules`
- `GET /api/v1/schedules?from&to` (기간 조회)
- `PUT /api/v1/schedules/{scheduleId}`
- `DELETE /api/v1/schedules/{scheduleId}`

### 구현 포인트
- 일정 CRUD 구현
- 기간 조회(월/주 단위 활용 가능한 from/to 쿼리)
- 소유자 검증 및 일정 단건 예외 처리
- 일정 에러코드 추가
  - `SC001 SCHEDULE_NOT_FOUND`

### 검증
- `./gradlew :api:compileJava` ✅

## S3-5 완료 ✅ (FE 체크리스트 화면 + API 연동, Shadcn/UI)

### Frontend
- `app/(main)/prepare/page.tsx` 추가
  - 체크리스트 목록/상세/항목 토글 UI
  - 체크리스트 생성 모달(Shadcn 스타일)
  - 준비 허브 탭 구조(체크리스트/예산/일정)
- `lib/api/s3.ts` 추가
  - 체크리스트 목록/생성/항목생성/완료토글 API 래핑

### 구현 포인트
- Shadcn 스타일 컴포넌트 조합 사용
  - `Tabs`, `Modal`, `Card`, `Badge`, `Input`, `Button`
- 진행률 바 + 상태 배지로 즉시 피드백
- `/prepare` 라우트와 하단 네비게이션 연동

### 검증
- `npm run build` ✅

## S3-6 완료 ✅ (FE 예산 화면 + API 연동, Shadcn/UI)

### Frontend
- `app/(main)/prepare/page.tsx`의 예산 탭 구현
  - 예산 기본정보 저장(총예산/통화/기간)
  - 카테고리 생성
  - 요약 지표 표시(총예산/계획합/지출합/잔여)
- `lib/api/s3.ts` 예산 API 클라이언트 확장
  - `upsertBudget`, `getBudgetSummary`, `createBudgetCategory`

### 구현 포인트
- Shadcn 스타일 컴포넌트 조합(`Card`, `Input`, `Badge`, `Button`, `Tabs`)
- 예산 저장 → 요약 즉시 갱신
- 카테고리 추가 후 목록/요약 갱신

### 검증
- `npm run build` ✅

## Next
- S3-7 FE 일정 화면 + S3-4 API 연동
