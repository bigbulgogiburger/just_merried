-- Users table
CREATE TABLE users (
    id              BIGSERIAL       PRIMARY KEY,
    email           VARCHAR(255)    NOT NULL UNIQUE,
    nickname        VARCHAR(50)     NOT NULL UNIQUE,
    profile_image_url VARCHAR(500),
    wedding_status  VARCHAR(20)     NOT NULL DEFAULT 'PREPARING',
    wedding_date    DATE,
    region          VARCHAR(50),
    role            VARCHAR(20)     NOT NULL DEFAULT 'USER',
    grade           VARCHAR(20)     NOT NULL DEFAULT 'FREE',
    provider        VARCHAR(20)     NOT NULL,
    provider_id     VARCHAR(255)    NOT NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_users_provider UNIQUE (provider, provider_id)
);

CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_provider ON users (provider, provider_id);

-- Couples table
CREATE TABLE couples (
    id              BIGSERIAL       PRIMARY KEY,
    invite_code     VARCHAR(20)     NOT NULL UNIQUE,
    status          VARCHAR(20)     NOT NULL DEFAULT 'PENDING',
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_couples_invite_code ON couples (invite_code);

-- Couple members table
CREATE TABLE couple_members (
    id              BIGSERIAL       PRIMARY KEY,
    couple_id       BIGINT          NOT NULL REFERENCES couples(id) ON DELETE CASCADE,
    user_id         BIGINT          NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_in_couple  VARCHAR(20)     NOT NULL,
    joined_at       TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_couple_user UNIQUE (couple_id, user_id)
);

CREATE INDEX idx_couple_members_couple_id ON couple_members (couple_id);
CREATE INDEX idx_couple_members_user_id ON couple_members (user_id);

-- User settings table
CREATE TABLE user_settings (
    id                  BIGSERIAL       PRIMARY KEY,
    user_id             BIGINT          NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    push_enabled        BOOLEAN         NOT NULL DEFAULT TRUE,
    marketing_agreed    BOOLEAN         NOT NULL DEFAULT FALSE,
    theme               VARCHAR(20)     NOT NULL DEFAULT 'LIGHT',
    language            VARCHAR(10)     NOT NULL DEFAULT 'ko'
);

CREATE INDEX idx_user_settings_user_id ON user_settings (user_id);

-- Business profiles table
CREATE TABLE business_profiles (
    id                  BIGSERIAL       PRIMARY KEY,
    user_id             BIGINT          NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    business_name       VARCHAR(100)    NOT NULL,
    business_number     VARCHAR(20)     NOT NULL,
    category            VARCHAR(50)     NOT NULL,
    address             VARCHAR(500),
    phone               VARCHAR(20),
    description         TEXT,
    status              VARCHAR(20)     NOT NULL DEFAULT 'PENDING',
    submitted_at        TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    approved_at         TIMESTAMP
);

CREATE INDEX idx_business_profiles_user_id ON business_profiles (user_id);
CREATE INDEX idx_business_profiles_status ON business_profiles (status);
CREATE INDEX idx_business_profiles_category ON business_profiles (category);
