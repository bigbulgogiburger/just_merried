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

## S6-4 완료 ✅ (이벤트 발행/저장 연계)

### 연계 범위
- 좋아요 발생 시 알림 생성
- 댓글 발생 시 알림 생성
- 팔로우 발생 시 알림 생성
- DM 메시지 전송 시 상대 참여자에게 알림 생성

### 구현 포인트
- `NotificationEventService` 추가
- 알림 생성 공통화(type/title/body/target/payload)
- 자기 자신 대상 알림 생성 방지
- 기존 서비스 연계
  - `InteractionService` (like/comment)
  - `FollowService`
  - `DmService`

### 검증
- `./gradlew :api:compileJava` ✅

## S6-5 완료 ✅ (실시간 전달 기초, SSE)

### API
- `GET /api/v1/community/realtime/subscribe`
  - 사용자별 SSE 구독 채널 연결

### 구현 포인트
- `SseBroker` 추가
  - 사용자별 emitter 관리
  - 연결/타임아웃/에러 시 정리
  - 이벤트 푸시(sendToUser)
- `RealtimeController` 추가
  - 인증 사용자 기준 SSE subscribe 엔드포인트
- 알림 이벤트 연계
  - `NotificationEventService`에서 알림 저장 후 SSE 즉시 발행

### 검증
- `./gradlew :api:compileJava` ✅

## S6-6 완료 ✅ (FE DM 화면, Shadcn/UI)

### Frontend
- `app/(main)/community/dm/page.tsx`
  - DM 방 생성/재사용 시작
  - 내 DM 방 목록
- `app/(main)/community/dm/[roomId]/page.tsx`
  - 메시지 타임라인
  - 메시지 입력/전송
  - 4초 주기 폴링 기반 갱신
- `frontend/lib/api/s6.ts`
  - DM API 클라이언트 래핑

### 구현 포인트
- Shadcn 스타일 컴포넌트 기반으로 DM 목록/상세 UX 정렬
- 메시지 전송 후 즉시 재조회로 최신화
- 최소 실시간 전략으로 polling 적용(SSE 연계는 S6-5 완료)

### 검증
- `npm run build` ✅

## S6-7 완료 ✅ (FE 알림센터)

### Frontend
- `app/(main)/notifications/page.tsx` 추가
  - 알림 목록
  - 단건 읽음/전체 읽음
  - 타입별 배지 및 대상 이동 링크
- `frontend/lib/api/s6.ts` 확장
  - `getNotifications`
  - `readNotification`
  - `readAllNotifications`

### 구현 포인트
- 알림 미읽음 카운트 노출
- 8초 폴링으로 최신 알림 반영
- 알림 타입별 목적지 라우팅
  - POST → `/community/{id}`
  - USER → `/community/user/{id}`
  - DM_ROOM → `/community/dm/{id}`

### 검증
- `npm run build` ✅

## S6-8 완료 ✅ (마감 문서/실행 검증/스크린샷)

### 문서
- `SPRINT_S6_CLOSE_REPORT.md` 작성

### 실행 검증
- Backend `:api:bootRun` 실행 시도
  - springdoc 버전 이슈 해결 후 DB 연결 단계까지 진행
- Frontend `npm run dev -- --port 3100` 실행
- 메뉴별 실행 스크린샷 11종 확보 (`screenshots/s6/*.png`)

## Next
- Sprint7 범위 정의 및 S7-1 착수
