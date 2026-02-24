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

## Next
- S5-2 게시글 API (작성/상세/삭제 + 권한 정책)
