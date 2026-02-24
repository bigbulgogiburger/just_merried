# Sprint 6 진행 현황

## S6-1 완료 ✅ (DM/알림 데이터·도메인 기반)

### Flyway
- `backend/domain/src/main/resources/db/migration/V5__init_sprint6_dm_notification.sql`
  - `chat_rooms`, `chat_participants`, `chat_messages`
  - `notifications`, `notification_reads`
  - room/user/unread 조회 중심 인덱스 반영

### Domain Entity/Repository
- Entity
  - `ChatRoom`, `ChatParticipant`, `ChatMessage`
  - `Notification`, `NotificationRead`
  - `ChatRoomType`, `ChatMessageType`, `NotificationType`
- Repository
  - `ChatRoomRepository`, `ChatParticipantRepository`, `ChatMessageRepository`
  - `NotificationRepository`, `NotificationReadRepository`

### 검증
- `./gradlew :domain:compileJava :api:compileJava` ✅

## S6-2 완료 ✅ (DM API)

### API
- `POST /api/v1/community/dm/rooms` (생성/재사용)
- `GET /api/v1/community/dm/rooms`
- `GET /api/v1/community/dm/rooms/{roomId}/messages`
- `POST /api/v1/community/dm/rooms/{roomId}/messages`

### 구현 포인트
- 1:1 DM 방 재사용 로직
- 참여자 검증(비참여자 접근 차단)
- 메시지 조회/전송 구현
- DM 관련 예외코드 추가
  - `CM006 DM_ROOM_NOT_FOUND`
  - `CM007 DM_ROOM_ACCESS_DENIED`

### 검증
- `./gradlew :api:compileJava` ✅

## S6-3 완료 ✅ (알림 API)

### API
- `GET /api/v1/community/notifications`
- `PATCH /api/v1/community/notifications/{id}/read`
- `PATCH /api/v1/community/notifications/read-all`

### 구현 포인트
- 알림 목록 + 읽음 여부 결합 응답
- 단건 읽음 처리(소유자 검증)
- 전체 읽음 처리
- 알림 예외코드 추가
  - `CM008 NOTIFICATION_NOT_FOUND`

### 검증
- `./gradlew :api:compileJava` ✅

## Next
- S6-4 이벤트 발행/저장 연계 (좋아요/댓글/팔로우/DM)
