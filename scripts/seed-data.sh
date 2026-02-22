#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-5432}"
DB_NAME="${DB_NAME:-weddingmate_dev}"
DB_USER="${DB_USERNAME:-weddingmate}"
DB_PASS="${DB_PASSWORD:-weddingmate_local}"

export PGPASSWORD="$DB_PASS"

echo "=========================================="
echo " WeddingMate - Seed Development Data"
echo "=========================================="
echo ""
echo "Target: $DB_USER@$DB_HOST:$DB_PORT/$DB_NAME"
echo ""

# Check PostgreSQL connectivity
if ! psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" -c "SELECT 1" &> /dev/null; then
  echo "[ERROR] Cannot connect to PostgreSQL. Is the database running?"
  echo "  Run: make up"
  exit 1
fi

echo "Inserting seed data..."
echo ""

psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" <<'SQL'
-- ==========================================
-- Sample Users
-- ==========================================
INSERT INTO users (email, nickname, provider, provider_id, role, created_at, updated_at)
VALUES
  ('bride@example.com', '신부', 'KAKAO', 'kakao_001', 'USER', NOW(), NOW()),
  ('groom@example.com', '신랑', 'NAVER', 'naver_001', 'USER', NOW(), NOW()),
  ('planner@example.com', '웨딩플래너', 'GOOGLE', 'google_001', 'PLANNER', NOW(), NOW())
ON CONFLICT DO NOTHING;

-- ==========================================
-- Sample Wedding
-- ==========================================
INSERT INTO weddings (title, wedding_date, budget_total, created_at, updated_at)
VALUES
  ('우리의 결혼식', '2026-10-10', 50000000, NOW(), NOW())
ON CONFLICT DO NOTHING;

-- ==========================================
-- Sample Budget Categories
-- ==========================================
INSERT INTO budget_categories (name, planned_amount, created_at, updated_at)
VALUES
  ('웨딩홀', 15000000, NOW(), NOW()),
  ('스드메', 5000000, NOW(), NOW()),
  ('예물/예단', 10000000, NOW(), NOW()),
  ('허니문', 5000000, NOW(), NOW()),
  ('청첩장', 500000, NOW(), NOW()),
  ('기타', 5000000, NOW(), NOW())
ON CONFLICT DO NOTHING;

-- ==========================================
-- Sample Checklist Items
-- ==========================================
INSERT INTO checklist_items (title, category, due_date, is_completed, created_at, updated_at)
VALUES
  ('웨딩홀 투어', 'VENUE', '2026-03-01', false, NOW(), NOW()),
  ('스드메 업체 미팅', 'PHOTO', '2026-04-01', false, NOW(), NOW()),
  ('청첩장 디자인 선택', 'INVITATION', '2026-07-01', false, NOW(), NOW()),
  ('허니문 장소 결정', 'HONEYMOON', '2026-05-01', false, NOW(), NOW()),
  ('예식 음악 선곡', 'CEREMONY', '2026-08-01', false, NOW(), NOW())
ON CONFLICT DO NOTHING;

SQL

echo "[OK] Seed data inserted successfully."
echo ""
echo "Note: If tables don't exist yet, run migrations first:"
echo "  make db-migrate"
echo ""
