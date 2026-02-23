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

## Next
- S3-5 FE 체크리스트 화면 + API 연동
