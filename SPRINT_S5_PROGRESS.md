# Sprint 5 진행 현황

## S5-1 완료 ✅ (데이터/도메인 기반)

### Flyway
- `backend/domain/src/main/resources/db/migration/V4__init_sprint5_community.sql`
  - `posts`, `post_media`, `hashtags`, `post_hashtags`
  - `likes`, `comments`, `follows`
  - 작성시각/사용자/지역/해시태그/카운트 조회용 인덱스 반영

### Domain Entity/Repository
- Entity
  - `Post`, `PostMedia`, `Hashtag`, `PostHashtag`
  - `PostLike`, `Comment`, `Follow`
- Repository
  - `PostRepository`, `PostMediaRepository`
  - `HashtagRepository`, `PostHashtagRepository`
  - `PostLikeRepository`, `CommentRepository`, `FollowRepository`

### 검증
- `./gradlew :domain:compileJava :api:compileJava` ✅

## S5-2 완료 ✅ (게시글 API)

### API
- `POST /api/v1/community/posts`
- `GET /api/v1/community/posts/{id}`
- `DELETE /api/v1/community/posts/{id}`

### 구현 포인트
- 게시글 작성/상세/삭제 구현
- 작성 권한 정책: USER만 작성 가능
- 본인 게시글만 삭제 가능
- 미디어/해시태그 저장 연계
  - 해시태그 정규화 + usageCount 증가
- 커뮤니티 예외코드 추가
  - `CM001 POST_NOT_FOUND`
  - `CM002 POST_ACCESS_DENIED`

### 검증
- `./gradlew :api:compileJava` ✅

## S5-3 완료 ✅ (피드 API)

### API
- `GET /api/v1/community/feed` (전체)
- `GET /api/v1/community/feed/following` (팔로잉)
- `GET /api/v1/community/feed/region` (지역)

### 구현 포인트
- page/size 기반 피드 조회
- 팔로잉 피드는 follow 관계 기반으로 작성자 필터링
- 지역 피드는 region 조건으로 조회

### 검증
- `./gradlew :api:compileJava` ✅

## Next
- S5-4 좋아요/댓글 API
