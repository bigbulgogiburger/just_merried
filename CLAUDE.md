# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

WeddingMate - 결혼 준비부터 결혼 생활까지 지원하는 올인원 플랫폼. Monorepo with backend (Spring Boot), frontend (Next.js), and mobile (Flutter).

## Build & Run Commands

### Infrastructure (Docker)
```bash
make up              # Start PostgreSQL, Redis, ES, Kafka via docker-compose
make down            # Stop all services
make logs            # Tail service logs
make db-migrate      # Run Flyway migrations
make clean           # Clean all build artifacts
```

### Backend (Spring Boot 3.3.6 / Java 21 / Gradle)
```bash
cd backend && ./gradlew clean build       # Full build
cd backend && ./gradlew :api:bootRun      # Run server (localhost:8080)
cd backend && ./gradlew test              # Run all tests
cd backend && ./gradlew :api:bootJar      # Build JAR
```

### Frontend (Next.js 14 / TypeScript / Node 20+)
```bash
cd frontend && npm install                # Install deps
cd frontend && npm run dev                # Dev server (localhost:3000)
cd frontend && npm run build              # Production build
cd frontend && npm run test               # Vitest watch mode
cd frontend && npm run test:run           # Single test run
cd frontend && npm run test:coverage      # Coverage report
cd frontend && npm run lint               # ESLint
cd frontend && npm run type-check         # TypeScript check
cd frontend && npm run format             # Prettier format
```

### Mobile (Flutter 3.x / Dart 3)
```bash
cd mobile && flutter pub get              # Install deps
cd mobile && flutter run                  # Run app
cd mobile && flutter test                 # Run tests
cd mobile && flutter pub run build_runner build   # Code gen (freezed, retrofit)
```

## Architecture

### Monorepo Layout
- `backend/` — Spring Boot multi-module Gradle project (Java 21)
- `frontend/` — Next.js 14 App Router web application
- `mobile/` — Flutter clean architecture mobile app
- `design-system/` — Shared design tokens (JSON, CSS, Tailwind, Flutter exports)
- `infra/` — K8s manifests (`k8s/`) and Terraform AWS IaC (`terraform/`)

### Backend Modules (Gradle)
- **api** → REST controllers, DTOs, Spring Security config. Depends on: common, domain, infra
- **domain** → JPA entities, repositories, services. Depends on: common
- **infra** → External integrations (JWT, OAuth, S3, Redis). Depends on: common
- **common** → `ApiResponse<T>`, `ErrorCode` enum, `GlobalExceptionHandler`, custom exceptions

Package root: `com.weddingmate.*`

### Frontend Layers
- `app/(auth)/` — Auth layout (login, onboarding)
- `app/(main)/` — Main layout with bottom tab navigation (홈/결혼준비/커뮤니티/마켓/MY)
- `components/ui/` — shadcn/ui-style atomic components (Button, Input, Card, Modal, Toast, etc.)
- `lib/api/client.ts` — Axios instance with JWT auto-refresh interceptor
- `stores/auth-store.ts` — Zustand + persist middleware for auth state
- `lib/providers/query-provider.tsx` — TanStack Query v5 provider

### Mobile Layers (Clean Architecture)
- `lib/core/` — Theme, router (go_router), constants, push notifications (FCM)
- `lib/data/` — Dio API client with auth/error interceptors, Hive local storage
- `lib/domain/` — Entities, abstract repositories, use cases
- `lib/presentation/` — Screens, widgets, Riverpod providers

### Auth Flow
OAuth2 social login (Kakao/Naver/Google/Apple) → Backend validates → Issues JWT pair (RS256):
- Access token: 30 min, Refresh token: 14 days
- Clients auto-attach `Authorization: Bearer <token>` via interceptor
- 401 triggers automatic token refresh + request retry
- Backend: `JwtAuthenticationFilter` in Spring Security filter chain
- Endpoints: `POST /api/v1/auth/login/{provider}`, `/refresh`, `/logout`

### API Response Format
```json
{"success": true, "message": "...", "data": {}, "timestamp": "..."}
{"success": false, "error": {"code": "ERROR_CODE", "message": "..."}, "timestamp": "..."}
```
Backend uses `ApiResponse<T>` record + `ErrorCode` enum + `GlobalExceptionHandler`.

### Design Tokens
Defined in `design-system/tokens/` (DTCG format), exported to:
- CSS: `design-system/exports/css-variables.css`
- Tailwind: `design-system/exports/tailwind-preset.js` → consumed by `frontend/tailwind.config.ts`
- Flutter: `design-system/exports/flutter-theme.dart` → mirrored in `mobile/lib/core/theme/`
- Primary brand color: Rose Gold `#B76E79`, Secondary: Ivory `#FFFFF0`, Accent: Dusty Rose `#DCAE96`

## Local Service Ports
| Service | Port |
|---------|------|
| Backend API | 8080 |
| Frontend | 3000 |
| PostgreSQL | 5432 (weddingmate/weddingmate) |
| Redis | 6379 |
| Elasticsearch | 9200 |
| Kafka | 9092 |
| Kafka UI | 8989 |
| Swagger UI | 8080/swagger-ui.html |

## Environment Setup
1. `cp .env.example .env` and fill OAuth/AWS credentials
2. `make up` to start Docker services
3. `make db-migrate` for database schema
4. Run backend/frontend/mobile as needed

## Backend Profiles
- `application-local.yml` — Local Docker services, debug logging
- `application-dev.yml` — Dev server settings
- `application-prod.yml` — Swagger disabled, SSL Redis, stricter logging
