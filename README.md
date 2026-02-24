# WeddingMate

**결혼의 모든 순간을 함께** - Your Complete Wedding Planning Companion

WeddingMate is an all-in-one wedding planning platform that helps couples manage every aspect of their wedding, from budgeting and vendor management to guest lists and scheduling.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 21, Spring Boot 3.x, Gradle |
| Frontend | Next.js 15, React 19, TypeScript, Tailwind CSS |
| Mobile | Flutter 3, Dart |
| Database | PostgreSQL 16, Redis 7, Elasticsearch 8 |
| Messaging | Apache Kafka 3 |
| Infrastructure | AWS (EKS, RDS, ElastiCache, S3, CloudFront) |
| IaC | Terraform, Kubernetes |
| CI/CD | GitHub Actions |

## Prerequisites

- **Java** 21+ (with Gradle wrapper included)
- **Node.js** 20+
- **Flutter** 3.x
- **Docker** & Docker Compose
- **AWS CLI** (for deployment)
- **Terraform** 1.5+ (for infrastructure provisioning)

## Quick Start

### 1. Clone the repository

```bash
git clone https://github.com/your-org/just_married.git
cd just_married
```

### 2. Set up environment variables

```bash
cp .env.example .env
# Edit .env with your local values
```

### 3. Start infrastructure services

```bash
make up
# or
docker compose up -d
```

### 4. Run backend

```bash
make be-run
# or
cd backend && ./gradlew :weddingmate-api:bootRun
```

### 5. Run frontend

```bash
make fe-dev
# or
cd frontend && npm run dev
```

### 6. Run mobile app

```bash
cd mobile && flutter run
```

### First-time setup (automated)

```bash
./scripts/setup-local.sh
```

## Project Structure

```
just_married/
├── backend/            # Spring Boot backend (multi-module Gradle)
│   ├── weddingmate-api/
│   ├── weddingmate-domain/
│   └── weddingmate-common/
├── frontend/           # Next.js web frontend
│   ├── src/
│   └── public/
├── mobile/             # Flutter mobile app
│   └── lib/
├── design-system/      # Shared design tokens & specs
├── infra/
│   ├── k8s/            # Kubernetes manifests
│   └── terraform/      # AWS infrastructure as code
├── scripts/            # Development & utility scripts
├── docker-compose.yml  # Local development services
├── Makefile            # Common development commands
└── .env.example        # Environment variable template
```

## Available Make Commands

| Command | Description |
|---------|-------------|
| `make help` | Show all available commands |
| `make up` | Start Docker services |
| `make down` | Stop Docker services |
| `make logs` | Follow Docker service logs |
| `make ps` | Show running services |
| `make db-migrate` | Run database migrations |
| `make be-run` | Start backend server |
| `make fe-dev` | Start frontend dev server |
| `make clean` | Clean all build artifacts |

## Local Services

| Service | URL/Port |
|---------|---------|
| Backend API | http://localhost:8082 |
| Frontend | http://localhost:3000 |
| PostgreSQL | localhost:5432 |
| Redis | localhost:6379 |
| Elasticsearch | http://localhost:9200 |
| Kafka | localhost:9092 |
| Kafka UI | http://localhost:8989 |

## Environment Variables

See `.env.example` for a complete list of required environment variables with descriptions.

## Contributing

1. Create a feature branch from `main`
2. Follow conventional commit messages (`feat:`, `fix:`, `refactor:`, etc.)
3. Write tests for new features
4. Submit a pull request for review

## License

All rights reserved.
