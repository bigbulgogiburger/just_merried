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

## Next
- S6-2 DM API (방 생성/목록/메시지 조회·전송)
