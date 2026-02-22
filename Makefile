.PHONY: up down logs ps db-migrate be-run fe-dev clean help

help: ## Show this help message
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-20s\033[0m %s\n", $$1, $$2}'

up: ## Start all Docker services
	docker compose up -d

down: ## Stop all Docker services
	docker compose down

logs: ## Follow Docker service logs
	docker compose logs -f

ps: ## Show running Docker services
	docker compose ps

db-migrate: ## Run Flyway database migrations
	cd backend && ./gradlew :weddingmate-api:flywayMigrate

be-run: ## Start backend server locally
	cd backend && ./gradlew :weddingmate-api:bootRun

fe-dev: ## Start frontend dev server
	cd frontend && npm run dev

clean: ## Clean all build artifacts
	cd backend && ./gradlew clean 2>/dev/null || true
	cd frontend && rm -rf .next node_modules/.cache 2>/dev/null || true
	cd mobile && flutter clean 2>/dev/null || true
	docker compose down -v --remove-orphans 2>/dev/null || true

.DEFAULT_GOAL := help
