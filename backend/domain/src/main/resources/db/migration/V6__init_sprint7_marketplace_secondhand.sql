CREATE TABLE market_categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(80) NOT NULL,
    slug VARCHAR(80) NOT NULL UNIQUE,
    sort_order INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE market_products (
    id BIGSERIAL PRIMARY KEY,
    seller_user_id BIGINT,
    category_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    base_price BIGINT NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_market_products_seller_user FOREIGN KEY (seller_user_id) REFERENCES users(id),
    CONSTRAINT fk_market_products_category FOREIGN KEY (category_id) REFERENCES market_categories(id)
);

CREATE TABLE market_product_options (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    option_name VARCHAR(120) NOT NULL,
    extra_price BIGINT NOT NULL DEFAULT 0,
    stock_quantity INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_market_product_options_product FOREIGN KEY (product_id) REFERENCES market_products(id) ON DELETE CASCADE
);

CREATE TABLE market_product_images (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    sort_order INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_market_product_images_product FOREIGN KEY (product_id) REFERENCES market_products(id) ON DELETE CASCADE
);

CREATE TABLE secondhand_products (
    id BIGSERIAL PRIMARY KEY,
    seller_user_id BIGINT NOT NULL,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    price BIGINT NOT NULL,
    region VARCHAR(80) NOT NULL,
    condition_status VARCHAR(20) NOT NULL,
    trade_method VARCHAR(20) NOT NULL,
    sale_status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_secondhand_products_seller_user FOREIGN KEY (seller_user_id) REFERENCES users(id)
);

CREATE TABLE secondhand_product_images (
    id BIGSERIAL PRIMARY KEY,
    secondhand_product_id BIGINT NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    sort_order INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_secondhand_product_images_product FOREIGN KEY (secondhand_product_id) REFERENCES secondhand_products(id) ON DELETE CASCADE
);

CREATE TABLE market_wishlists (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    target_type VARCHAR(20) NOT NULL,
    target_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_market_wishlists_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT uk_market_wishlist UNIQUE (user_id, target_type, target_id)
);

CREATE INDEX idx_market_products_category_status ON market_products(category_id, status);
CREATE INDEX idx_market_products_created_at ON market_products(created_at DESC);
CREATE INDEX idx_secondhand_products_region_status ON secondhand_products(region, sale_status);
CREATE INDEX idx_secondhand_products_created_at ON secondhand_products(created_at DESC);
CREATE INDEX idx_market_wishlists_user_created ON market_wishlists(user_id, created_at DESC);
