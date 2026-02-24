# Sprint 9 보안 점검 체크리스트

## 점검 내역
- [x] 모든 life/admin API 인증 컨텍스트 기반 사용자 식별(`@CurrentUser`)
- [x] 사용자 리소스 접근 시 소유권 검증(타 사용자 데이터 접근 차단)
- [x] DTO 기반 입력 검증(`@Valid`, `@NotBlank`, `@NotNull`, 범위 제한)
- [x] 직접 SQL 문자열 조합 미사용(JPA Repository 사용)

## 유지/개선 항목
- [ ] 관리자 API에 role 기반 권한 어노테이션 강제(현재는 경량 구현)
- [ ] Rate limiting 정책을 `/api/v1/life/**`에도 명시 적용
- [ ] 보안 회귀 테스트 케이스 추가
