CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    order_type VARCHAR(30) NOT NULL,
    order_id BIGINT,
    amount BIGINT NOT NULL,
    payment_method VARCHAR(30) NOT NULL,
    provider VARCHAR(30) NOT NULL,
    provider_tx_id VARCHAR(120),
    status VARCHAR(20) NOT NULL,
    approved_at TIMESTAMP,
    canceled_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE escrow_transactions (
    id BIGSERIAL PRIMARY KEY,
    secondhand_product_id BIGINT NOT NULL REFERENCES secondhand_products(id),
    buyer_user_id BIGINT NOT NULL REFERENCES users(id),
    seller_user_id BIGINT NOT NULL REFERENCES users(id),
    amount BIGINT NOT NULL,
    fee_amount BIGINT NOT NULL DEFAULT 0,
    status VARCHAR(30) NOT NULL,
    tracking_number VARCHAR(80),
    carrier VARCHAR(40),
    paid_at TIMESTAMP,
    shipped_at TIMESTAMP,
    confirmed_at TIMESTAMP,
    auto_confirm_due_at TIMESTAMP,
    settled_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE settlements (
    id BIGSERIAL PRIMARY KEY,
    seller_user_id BIGINT NOT NULL REFERENCES users(id),
    settlement_type VARCHAR(30) NOT NULL,
    reference_id BIGINT,
    gross_amount BIGINT NOT NULL,
    fee_rate NUMERIC(5,2) NOT NULL,
    fee_amount BIGINT NOT NULL,
    net_amount BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    settled_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE price_offers (
    id BIGSERIAL PRIMARY KEY,
    secondhand_product_id BIGINT NOT NULL REFERENCES secondhand_products(id),
    buyer_user_id BIGINT NOT NULL REFERENCES users(id),
    seller_user_id BIGINT NOT NULL REFERENCES users(id),
    offered_price BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    message VARCHAR(300),
    responded_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE trade_reviews (
    id BIGSERIAL PRIMARY KEY,
    escrow_transaction_id BIGINT NOT NULL REFERENCES escrow_transactions(id),
    reviewer_user_id BIGINT NOT NULL REFERENCES users(id),
    reviewee_user_id BIGINT NOT NULL REFERENCES users(id),
    score_kindness INT NOT NULL,
    score_punctuality INT NOT NULL,
    score_quality INT NOT NULL,
    content TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE subscriptions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    plan_type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    started_at TIMESTAMP,
    ended_at TIMESTAMP,
    renew_at TIMESTAMP,
    auto_renew BOOLEAN NOT NULL DEFAULT TRUE,
    last_payment_id BIGINT REFERENCES payments(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE users ADD COLUMN IF NOT EXISTS manner_temperature NUMERIC(4,1) NOT NULL DEFAULT 36.5;

CREATE INDEX idx_escrow_buyer ON escrow_transactions(buyer_user_id);
CREATE INDEX idx_escrow_seller ON escrow_transactions(seller_user_id);
CREATE INDEX idx_price_offers_product ON price_offers(secondhand_product_id);
CREATE INDEX idx_settlements_seller ON settlements(seller_user_id);
CREATE INDEX idx_subscriptions_user ON subscriptions(user_id);
