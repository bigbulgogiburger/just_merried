# Sprint 6 작업 분해 (DM · 알림센터 · 실시간 업데이트)

작성일: 2026-02-24

## 목표
- 커뮤니티 상호작용 이후 후속 소통 채널(DM/알림)을 완성
- 좋아요/댓글/팔로우 이벤트의 사용자 체감 속도를 개선

---

## S6-1 데이터/도메인 기반
- DDL/Flyway
  - `chat_rooms`, `chat_participants`, `chat_messages`
  - `notifications`, `notification_reads`
- 인덱스
  - room_id + created_at, user_id + created_at, unread 조회 최적화
- 도메인 엔티티/리포지토리 생성

## S6-2 DM API
- `POST /api/v1/community/dm/rooms` (대화방 생성/재사용)
- `GET /api/v1/community/dm/rooms` (내 대화방 목록)
- `GET /api/v1/community/dm/rooms/{id}/messages`
- `POST /api/v1/community/dm/rooms/{id}/messages`

## S6-3 알림 API
- `GET /api/v1/community/notifications`
- `PATCH /api/v1/community/notifications/{id}/read`
- `PATCH /api/v1/community/notifications/read-all`
- 타입별 알림 payload 설계(좋아요/댓글/팔로우/시스템)

## S6-4 이벤트 발행/저장 연계
- 좋아요/댓글/팔로우 발생 시 알림 생성
- DM 메시지 저장 시 알림/읽음 상태 동기화
- 알림 중복 억제 규칙(MVP)

## S6-5 실시간 전달 기초
- WebSocket or SSE 최소 경로 구축
- 신규 메시지/알림 push 전달
- 재연결 시 누락 복구 전략(최신 N개 fetch)

## S6-6 FE DM 화면 (Shadcn/ui)
- `/community/dm` 대화방 목록
- `/community/dm/[roomId]` 메시지 화면
- 입력/전송/타임라인/읽음 상태

## S6-7 FE 알림센터
- `/notifications` 리스트
- 타입별 아이콘/문구
- 단건 읽음/전체 읽음
- 탭 이동(대상 게시글/사용자/DM)

## S6-8 마감
- Sprint6 진행문서/종결리포트
- 검증 결과(backend compile + frontend build)

---

## 실행 순서(고정)
1. S6-1
2. S6-2
3. S6-3
4. S6-4
5. S6-5
6. S6-6
7. S6-7
8. S6-8
