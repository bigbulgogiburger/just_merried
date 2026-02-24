# Sprint 10 부하 테스트 계획/결과

## 목표
- 피드 조회 구간에 대해 500 동시 사용자 수준에서 API 안정성 확인
- p95 450ms 이하, 오류율 1% 미만을 목표 기준으로 정의

## 실행 방법
- 스크립트: `scripts/k6/sprint10-feed-load.js`
- 예시: `k6 run -e BASE_URL=http://localhost:8082 scripts/k6/sprint10-feed-load.js`

## 결과 기록 (로컬 기준)
- 실행 환경: backend 8082 / db 5433 / mock oauth(local)
- 결과 요약: 임계치 통과(오류율 0%, p95 기준 통과)
- 비고: 더미 데이터 볼륨이 작은 환경이므로, 운영 직전 스테이징 재검증 필요

## 후속
- 채팅 동시 세션(500) 시나리오 추가
- 결제/에스크로 시나리오 별도 워크로드 분리
