# Sprint 6 종결 리포트

## 범위
- S6-1: DM/알림 스키마 + 도메인 baseline
- S6-2: DM API (방 생성·재사용/목록/메시지 조회·전송)
- S6-3: 알림 API (목록/단건 읽음/전체 읽음)
- S6-4: 이벤트 연계 (좋아요/댓글/팔로우/DM → 알림 생성)
- S6-5: SSE 실시간 구독 + 알림 푸시 브리지
- S6-6: FE DM 화면
- S6-7: FE 알림센터
- S6-8: 실행 검증 + 메뉴별 스크린샷 확보

## 실행/검증 결과

### Backend
- `./gradlew :api:compileJava` ✅
- `./gradlew :api:bootRun` 실행 확인
  - 초기 springdoc 충돌 해결(2.7.0 → 2.6.0)
  - 이후 DB 연결 단계에서 실패(로컬 5432 포트 점유/DB 연결 불가 환경)

### Frontend
- `npm run build` ✅
- `npm run dev -- --port 3100` ✅
- 실제 구동 URL: `http://localhost:3100`

## 메뉴별 스크린샷 (실행 화면)
- `screenshots/s6/login.png`
- `screenshots/s6/onboarding.png`
- `screenshots/s6/home.png`
- `screenshots/s6/prepare.png`
- `screenshots/s6/vendors.png`
- `screenshots/s6/vendors_compare.png`
- `screenshots/s6/community.png`
- `screenshots/s6/community_dm.png`
- `screenshots/s6/notifications.png`
- `screenshots/s6/my_profile.png`
- `screenshots/s6/my_couple.png`

## Sprint6 결과 요약
- DM + 알림 + 실시간(SSE) + FE 화면까지 수직 슬라이스 구현 완료
- API/FE 빌드 기준 정상
- 런타임 기준 백엔드 DB 연결 환경만 정리되면 E2E 동작 확인 가능
