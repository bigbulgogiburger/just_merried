# Sprint 9 진행 현황

## S9-1 완료 ✅ 가족 구성원/행사/신혼집 도메인
- Flyway `V8__init_sprint9_married_life.sql` 추가
- `family_members`, `family_events`, `newhome_checklist_items` 테이블 및 인덱스 반영
- life 도메인 엔티티/리포지토리 추가

## S9-2 완료 ✅ 결혼 생활 API + 스케줄러
- `GET /api/v1/life/dashboard`
- `GET/POST/PUT/DELETE /api/v1/life/family/members`
- `GET/POST/PUT/DELETE /api/v1/life/family/events`
- `GET/POST/PUT/DELETE /api/v1/life/newhome`
- `FamilyEventAlarmService`(매일 09:00 점검)

## S9-3 완료 ✅ Admin 대시보드 API
- `GET /api/v1/admin/dashboard`
- 사용자/게시글/중고거래/업체심사 대기 건수 요약

## S9-4 완료 ✅ FE 결혼 생활 화면
- `/life` 결혼 생활 대시보드
- `/life/family` 가족 구성원/행사 관리
- `/life/newhome` 신혼집 체크리스트
- `frontend/lib/api/s9.ts` API 클라이언트 추가

## S9-5 완료 ✅ 점검 문서
- 성능 최적화 체크리스트/현황 문서
- 보안 점검 체크리스트 문서
- 접근성(a11y) 체크리스트 문서

## 검증
- `./gradlew :domain:compileJava :api:compileJava` ✅
- `npm run build` ✅
