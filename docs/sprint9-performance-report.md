# Sprint 9 성능 최적화 리포트

## 적용 내용
- 가족/신혼집 조회 쿼리용 인덱스 추가
  - `idx_family_events_user_date`
  - `idx_newhome_items_user_category`
- 대시보드 API는 최근 30일 데이터만 조회하도록 범위 제한
- FE 결혼 생활 페이지를 기능 단위 분리(`/life`, `/life/family`, `/life/newhome`)

## 빌드 기준 확인
- Backend compile 성공
- Frontend production build 성공

## 후속 최적화 항목
- 가족 행사 다건 알림 시 배치 조회 + 비동기 발행으로 전환
- 대시보드 API Redis 캐시(짧은 TTL) 적용 검토
- Lighthouse 측정 자동화(현재 수동)
