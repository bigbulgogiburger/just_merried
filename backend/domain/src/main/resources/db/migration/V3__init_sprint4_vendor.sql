-- Vendor categories
CREATE TABLE vendor_categories (
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(50) NOT NULL UNIQUE,
    sort_order      INT NOT NULL DEFAULT 0,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_vendor_categories_sort_order ON vendor_categories (sort_order);

-- Vendors
CREATE TABLE vendors (
    id                  BIGSERIAL PRIMARY KEY,
    owner_user_id       BIGINT REFERENCES users(id) ON DELETE SET NULL,
    category_id         BIGINT NOT NULL REFERENCES vendor_categories(id),
    name                VARCHAR(120) NOT NULL,
    description         TEXT,
    region              VARCHAR(50) NOT NULL,
    address             VARCHAR(255),
    phone               VARCHAR(30),
    homepage_url        VARCHAR(500),
    min_price           BIGINT NOT NULL DEFAULT 0,
    max_price           BIGINT NOT NULL DEFAULT 0,
    rating_avg          NUMERIC(3,2) NOT NULL DEFAULT 0,
    rating_count        INT NOT NULL DEFAULT 0,
    status              VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_vendors_category_id ON vendors (category_id);
CREATE INDEX idx_vendors_region ON vendors (region);
CREATE INDEX idx_vendors_price_range ON vendors (min_price, max_price);
CREATE INDEX idx_vendors_rating ON vendors (rating_avg DESC, rating_count DESC);
CREATE INDEX idx_vendors_created_at ON vendors (created_at DESC);

-- Vendor images
CREATE TABLE vendor_images (
    id              BIGSERIAL PRIMARY KEY,
    vendor_id       BIGINT NOT NULL REFERENCES vendors(id) ON DELETE CASCADE,
    image_url       VARCHAR(500) NOT NULL,
    is_cover        BOOLEAN NOT NULL DEFAULT FALSE,
    sort_order      INT NOT NULL DEFAULT 0,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_vendor_images_vendor_id ON vendor_images (vendor_id);

-- Vendor prices
CREATE TABLE vendor_prices (
    id              BIGSERIAL PRIMARY KEY,
    vendor_id       BIGINT NOT NULL REFERENCES vendors(id) ON DELETE CASCADE,
    package_name    VARCHAR(120) NOT NULL,
    price           BIGINT NOT NULL,
    description     TEXT,
    sort_order      INT NOT NULL DEFAULT 0,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_vendor_prices_vendor_id ON vendor_prices (vendor_id);

-- Favorites
CREATE TABLE favorites (
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    vendor_id       BIGINT NOT NULL REFERENCES vendors(id) ON DELETE CASCADE,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_favorites_user_vendor UNIQUE (user_id, vendor_id)
);

CREATE INDEX idx_favorites_user_id ON favorites (user_id);
CREATE INDEX idx_favorites_vendor_id ON favorites (vendor_id);

-- Compare items
CREATE TABLE compare_items (
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    vendor_id       BIGINT NOT NULL REFERENCES vendors(id) ON DELETE CASCADE,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_compare_items_user_vendor UNIQUE (user_id, vendor_id)
);

CREATE INDEX idx_compare_items_user_id ON compare_items (user_id);
CREATE INDEX idx_compare_items_vendor_id ON compare_items (vendor_id);

-- Reviews
CREATE TABLE reviews (
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    vendor_id       BIGINT NOT NULL REFERENCES vendors(id) ON DELETE CASCADE,
    rating          INT NOT NULL,
    content         TEXT,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_reviews_rating CHECK (rating BETWEEN 1 AND 5)
);

CREATE INDEX idx_reviews_vendor_id ON reviews (vendor_id);
CREATE INDEX idx_reviews_user_id ON reviews (user_id);
CREATE INDEX idx_reviews_created_at ON reviews (created_at DESC);
