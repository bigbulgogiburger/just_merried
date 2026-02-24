CREATE TABLE family_members (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(50) NOT NULL,
    relation VARCHAR(30) NOT NULL,
    birth_date DATE NOT NULL,
    lunar BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE family_events (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    family_member_id BIGINT REFERENCES family_members(id) ON DELETE SET NULL,
    title VARCHAR(100) NOT NULL,
    event_date DATE NOT NULL,
    lunar BOOLEAN NOT NULL DEFAULT FALSE,
    remind_days_before INT NOT NULL DEFAULT 1,
    repeat_yearly BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE newhome_checklist_items (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    category VARCHAR(30) NOT NULL,
    title VARCHAR(100) NOT NULL,
    memo VARCHAR(500),
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    assignee VARCHAR(30),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE report_queue (
    id BIGSERIAL PRIMARY KEY,
    report_type VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_family_members_user_id ON family_members(user_id);
CREATE INDEX idx_family_events_user_date ON family_events(user_id, event_date);
CREATE INDEX idx_newhome_items_user_category ON newhome_checklist_items(user_id, category);
CREATE INDEX idx_report_queue_status ON report_queue(status);
