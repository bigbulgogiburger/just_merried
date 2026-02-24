# Sprint 5 작업 분해 (SNS 피드 & 팔로우)

작성일: 2026-02-24

## 목표
- 커뮤니티 핵심 루프(게시글 작성 → 피드 소비 → 상호작용) MVP 완성
- 팔로우/좋아요/댓글/해시태그 기반 탐색 흐름 구축

---

## S5-1 데이터/도메인 기반
- DDL/Flyway
  - `posts`, `post_media`, `hashtags`, `post_hashtags`
  - `likes`, `comments`, `follows`
- 인덱스
  - 작성시각, 사용자, 지역, 해시태그, 좋아요 카운트 조회 최적화
- 도메인 엔티티/리포지토리 생성

## S5-2 게시글 API
- `POST /api/v1/community/posts` (작성)
- `GET /api/v1/community/posts/{id}` (상세)
- `DELETE /api/v1/community/posts/{id}` (삭제)
- 작성 권한 정책(USER만) 최소 반영

## S5-3 피드 API
- `GET /api/v1/community/feed` (전체)
- `GET /api/v1/community/feed/following` (팔로잉)
- `GET /api/v1/community/feed/region` (지역)
- 커서/페이지네이션 MVP(우선 page/size)

## S5-4 좋아요/댓글 API
- 좋아요 토글
  - `POST /api/v1/community/posts/{id}/like`
  - `DELETE /api/v1/community/posts/{id}/like`
- 댓글
  - `POST /api/v1/community/posts/{id}/comments`
  - `GET /api/v1/community/posts/{id}/comments`
  - `DELETE /api/v1/community/comments/{id}`

## S5-5 팔로우 API
- `POST /api/v1/community/users/{id}/follow`
- `DELETE /api/v1/community/users/{id}/follow`
- `GET /api/v1/community/users/{id}/followers`
- `GET /api/v1/community/users/{id}/following`

## S5-6 해시태그/탐색 API
- 해시태그 파싱 저장
- `GET /api/v1/community/hashtags/trending`
- `GET /api/v1/community/search` (키워드/해시태그/사용자)

## S5-7 FE 커뮤니티 핵심 화면 (Shadcn/ui)
- `/community` 피드 화면(전체/팔로잉/지역 탭)
- `/community/create` 작성 화면
- `/community/[id]` 상세 + 댓글
- `/community/user/[id]` 프로필 + 팔로우

## S5-8 마감
- Sprint5 진행문서/종결리포트
- 검증 결과(backend compile + frontend build)

---

## 실행 순서(고정)
1. S5-1
2. S5-2
3. S5-3
4. S5-4
5. S5-5
6. S5-6
7. S5-7
8. S5-8
