#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"

echo "=========================================="
echo " WeddingMate - Local Environment Setup"
echo "=========================================="
echo ""

# Check prerequisites
check_command() {
  if ! command -v "$1" &> /dev/null; then
    echo "[ERROR] $1 is not installed. $2"
    return 1
  else
    echo "[OK] $1 found: $($1 --version 2>&1 | head -1)"
  fi
}

echo "Checking prerequisites..."
echo ""

MISSING=0
check_command "java" "Install Java 21+ from https://adoptium.net" || MISSING=1
check_command "node" "Install Node.js 20+ from https://nodejs.org" || MISSING=1
check_command "docker" "Install Docker from https://docker.com" || MISSING=1
check_command "flutter" "Install Flutter from https://flutter.dev" || MISSING=1

echo ""

if [ "$MISSING" -ne 0 ]; then
  echo "[WARN] Some prerequisites are missing. You can still proceed with partial setup."
  read -p "Continue anyway? (y/N): " CONTINUE
  if [ "$CONTINUE" != "y" ] && [ "$CONTINUE" != "Y" ]; then
    echo "Setup aborted."
    exit 1
  fi
fi

# Create .env file
if [ ! -f .env ]; then
  echo "Creating .env from .env.example..."
  cp .env.example .env
  echo "[OK] .env created. Edit it with your values if needed."
else
  echo "[OK] .env already exists. Skipping."
fi
echo ""

# Start Docker services
echo "Starting Docker services..."
docker compose up -d
echo ""

# Wait for services to be healthy
echo "Waiting for services to be ready..."
echo ""

wait_for_service() {
  local name=$1
  local check_cmd=$2
  local max_retries=${3:-30}
  local retry=0

  printf "  Waiting for %-20s" "$name..."
  while [ $retry -lt $max_retries ]; do
    if eval "$check_cmd" &> /dev/null; then
      echo " Ready"
      return 0
    fi
    sleep 2
    retry=$((retry + 1))
  done
  echo " Timeout (service may still be starting)"
  return 1
}

wait_for_service "PostgreSQL" "docker exec weddingmate-postgres pg_isready -U weddingmate"
wait_for_service "Redis" "docker exec weddingmate-redis redis-cli ping"
wait_for_service "Elasticsearch" "curl -sf http://localhost:9200/_cluster/health"
wait_for_service "Kafka" "docker exec weddingmate-kafka kafka-broker-api-versions --bootstrap-server localhost:9092" 40

echo ""

# Install frontend dependencies
if [ -f frontend/package.json ]; then
  echo "Installing frontend dependencies..."
  cd frontend && npm install && cd ..
  echo "[OK] Frontend dependencies installed."
else
  echo "[SKIP] frontend/package.json not found."
fi
echo ""

# Install Flutter dependencies
if [ -f mobile/pubspec.yaml ]; then
  echo "Installing Flutter dependencies..."
  cd mobile && flutter pub get && cd ..
  echo "[OK] Flutter dependencies installed."
else
  echo "[SKIP] mobile/pubspec.yaml not found."
fi
echo ""

echo "=========================================="
echo " Setup Complete!"
echo "=========================================="
echo ""
echo " Start backend:   make be-run"
echo " Start frontend:  make fe-dev"
echo " Start mobile:    cd mobile && flutter run"
echo " View services:   make ps"
echo " View logs:       make logs"
echo ""
