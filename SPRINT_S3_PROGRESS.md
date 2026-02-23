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

## Next
- S3-2 체크리스트 API CRUD + 완료 토글/진행률
