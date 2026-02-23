-- Checklist
CREATE TABLE checklists (
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title           VARCHAR(120) NOT NULL,
    description     TEXT,
    start_date      DATE,
    due_date        DATE,
    status          VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_checklists_user_id ON checklists (user_id);
CREATE INDEX idx_checklists_due_date ON checklists (due_date);

CREATE TABLE checklist_items (
    id              BIGSERIAL PRIMARY KEY,
    checklist_id    BIGINT NOT NULL REFERENCES checklists(id) ON DELETE CASCADE,
    title           VARCHAR(150) NOT NULL,
    description     TEXT,
    assignee        VARCHAR(20) NOT NULL DEFAULT 'BOTH',
    completed       BOOLEAN NOT NULL DEFAULT FALSE,
    completed_at    TIMESTAMP,
    sort_order      INT NOT NULL DEFAULT 0,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_checklist_items_checklist_id ON checklist_items (checklist_id);
CREATE INDEX idx_checklist_items_completed ON checklist_items (completed);

-- Budget
CREATE TABLE budgets (
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    total_budget    BIGINT NOT NULL DEFAULT 0,
    currency        VARCHAR(10) NOT NULL DEFAULT 'KRW',
    start_date      DATE,
    end_date        DATE,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_budgets_user UNIQUE (user_id)
);

CREATE TABLE budget_categories (
    id              BIGSERIAL PRIMARY KEY,
    budget_id       BIGINT NOT NULL REFERENCES budgets(id) ON DELETE CASCADE,
    name            VARCHAR(80) NOT NULL,
    planned_amount  BIGINT NOT NULL DEFAULT 0,
    spent_amount    BIGINT NOT NULL DEFAULT 0,
    sort_order      INT NOT NULL DEFAULT 0,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_budget_categories_budget_id ON budget_categories (budget_id);

CREATE TABLE expenses (
    id                  BIGSERIAL PRIMARY KEY,
    budget_category_id  BIGINT NOT NULL REFERENCES budget_categories(id) ON DELETE CASCADE,
    user_id             BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title               VARCHAR(120) NOT NULL,
    amount              BIGINT NOT NULL,
    expense_date        DATE NOT NULL,
    memo                TEXT,
    payer_type          VARCHAR(20) NOT NULL DEFAULT 'TOGETHER',
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_expenses_budget_category_id ON expenses (budget_category_id);
CREATE INDEX idx_expenses_user_id ON expenses (user_id);
CREATE INDEX idx_expenses_expense_date ON expenses (expense_date);

-- Schedule
CREATE TABLE schedules (
    id                  BIGSERIAL PRIMARY KEY,
    user_id             BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title               VARCHAR(120) NOT NULL,
    description         TEXT,
    start_at            TIMESTAMP NOT NULL,
    end_at              TIMESTAMP NOT NULL,
    all_day             BOOLEAN NOT NULL DEFAULT FALSE,
    shared_with_couple  BOOLEAN NOT NULL DEFAULT TRUE,
    reminder_minutes    INT,
    status              VARCHAR(20) NOT NULL DEFAULT 'PLANNED',
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_schedules_user_id ON schedules (user_id);
CREATE INDEX idx_schedules_start_at ON schedules (start_at);
CREATE INDEX idx_schedules_status ON schedules (status);
