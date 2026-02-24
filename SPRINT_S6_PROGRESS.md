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

## Next
- S6-3 알림 API (목록/단건읽음/전체읽음)
