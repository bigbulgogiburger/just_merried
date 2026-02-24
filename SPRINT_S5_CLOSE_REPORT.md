# Sprint 5 종결 리포트 (SNS 피드 & 팔로우)

작성일: 2026-02-24

## 1) Sprint 목표
- 커뮤니티 핵심 루프(작성 → 피드 → 상세 상호작용) 구축
- 팔로우/좋아요/댓글/해시태그 탐색 기반 완성
- Shadcn/ui 기반 FE 일관 UX 적용

## 2) 완료 범위

### Backend
- 커뮤니티 도메인/스키마
  - `V4__init_sprint5_community.sql`
  - posts, post_media, hashtags, post_hashtags, likes, comments, follows
- 게시글 API
  - `POST /api/v1/community/posts`
  - `GET /api/v1/community/posts/{id}`
  - `DELETE /api/v1/community/posts/{id}`
  - 권한 정책: USER만 작성 가능
- 피드 API
  - `GET /api/v1/community/feed`
  - `GET /api/v1/community/feed/following`
  - `GET /api/v1/community/feed/region`
- 좋아요/댓글 API
  - `POST/DELETE /api/v1/community/posts/{id}/like`
  - `POST/GET /api/v1/community/posts/{id}/comments`
  - `DELETE /api/v1/community/comments/{id}`
- 팔로우 API
  - `POST/DELETE /api/v1/community/users/{id}/follow`
  - `GET /api/v1/community/users/{id}/followers`
  - `GET /api/v1/community/users/{id}/following`
- 해시태그/탐색 API
  - `GET /api/v1/community/hashtags/trending`
  - `GET /api/v1/community/search?q=...`

### Frontend
- `/community` 피드 화면(전체/팔로잉/지역)
- `/community/create` 게시글 작성
- `/community/[id]` 게시글 상세 + 좋아요/댓글
- `/community/user/[id]` 유저 프로필 + 팔로우
- API 클라이언트
  - `frontend/lib/api/s5.ts`

## 3) 주요 산출물 파일
- `SPRINT_S5_BREAKDOWN.md`
- `SPRINT_S5_PROGRESS.md`
- `SPRINT_S5_CLOSE_REPORT.md`
- Backend
  - `backend/domain/src/main/resources/db/migration/V4__init_sprint5_community.sql`
  - `backend/domain/src/main/java/com/weddingmate/domain/community/*`
  - `backend/api/src/main/java/com/weddingmate/api/community/*`
- Frontend
  - `frontend/lib/api/s5.ts`
  - `frontend/app/(main)/community/*`

## 4) 검증
- Backend compile
  - `./gradlew :domain:compileJava :api:compileJava` ✅
- Frontend build
  - `npm run build` ✅
- 사용자 요청에 따라 테스트 실행은 생략

## 5) Sprint 5 DoD 판정
- [x] SNS 핵심 도메인/스키마 구축
- [x] 게시글/피드/좋아요/댓글/팔로우/탐색 API 구현
- [x] 커뮤니티 FE 핵심 화면 구현
- [x] Shadcn/ui 기반 UI 일관성 유지
- [x] 컴파일/빌드 검증 통과 및 main 반영 완료

## 6) Sprint 6 진입 권장 백로그
1. DM/알림센터 도메인 및 이벤트 모델 정의
2. 알림 API + 타입별 라우팅(좋아요/댓글/팔로우/시스템)
3. 실시간 업데이트 전략(WebSocket/SSE) 최소 경로 구현
4. 커뮤니티 검색 고도화(해시태그/사용자/게시글 탭 UI)
