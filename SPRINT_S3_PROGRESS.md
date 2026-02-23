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

## Next
- S3-3 예산 API (카테고리/지출 CRUD + 요약 통계)
