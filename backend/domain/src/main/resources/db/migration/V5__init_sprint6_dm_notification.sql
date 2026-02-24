-- DM chat rooms
CREATE TABLE chat_rooms (
    id              BIGSERIAL PRIMARY KEY,
    type            VARCHAR(20) NOT NULL DEFAULT 'DIRECT',
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_chat_rooms_created_at ON chat_rooms (created_at DESC);

-- Participants
CREATE TABLE chat_participants (
    id              BIGSERIAL PRIMARY KEY,
    room_id         BIGINT NOT NULL REFERENCES chat_rooms(id) ON DELETE CASCADE,
    user_id         BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    last_read_message_id BIGINT,
    joined_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_chat_participant UNIQUE (room_id, user_id)
);

CREATE INDEX idx_chat_participants_room_id ON chat_participants (room_id);
CREATE INDEX idx_chat_participants_user_id ON chat_participants (user_id);

-- Messages
CREATE TABLE chat_messages (
    id              BIGSERIAL PRIMARY KEY,
    room_id         BIGINT NOT NULL REFERENCES chat_rooms(id) ON DELETE CASCADE,
    sender_id       BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    content         TEXT NOT NULL,
    message_type    VARCHAR(20) NOT NULL DEFAULT 'TEXT',
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_chat_messages_room_created ON chat_messages (room_id, created_at DESC);
CREATE INDEX idx_chat_messages_sender_id ON chat_messages (sender_id);

-- Notifications
CREATE TABLE notifications (
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    type            VARCHAR(30) NOT NULL,
    title           VARCHAR(120) NOT NULL,
    body            TEXT,
    target_type     VARCHAR(30),
    target_id       BIGINT,
    payload_json    TEXT,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_notifications_user_created ON notifications (user_id, created_at DESC);
CREATE INDEX idx_notifications_type ON notifications (type);

-- Notification read states
CREATE TABLE notification_reads (
    id                  BIGSERIAL PRIMARY KEY,
    notification_id     BIGINT NOT NULL REFERENCES notifications(id) ON DELETE CASCADE,
    user_id             BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    read_at             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_notification_read UNIQUE (notification_id, user_id)
);

CREATE INDEX idx_notification_reads_user_id ON notification_reads (user_id);
CREATE INDEX idx_notification_reads_notification_id ON notification_reads (notification_id);
