package com.weddingmate.api.life;

import com.weddingmate.api.life.dto.LifeDtos.*;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.domain.life.entity.NewHomeCategory;
import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequiredArgsConstructor @RequestMapping("/api/v1/life")
public class LifeController {
    private final LifeService lifeService;

    @GetMapping("/dashboard") public ResponseEntity<ApiResponse<LifeDashboardResponse>> dashboard(@CurrentUser UserPrincipal p){ return ResponseEntity.ok(ApiResponse.ok(lifeService.dashboard(p.userId()))); }

    @GetMapping("/family/members") public ResponseEntity<ApiResponse<List<FamilyMemberResponse>>> members(@CurrentUser UserPrincipal p){ return ResponseEntity.ok(ApiResponse.ok(lifeService.members(p.userId()))); }
    @PostMapping("/family/members") public ResponseEntity<ApiResponse<FamilyMemberResponse>> createMember(@CurrentUser UserPrincipal p,@Valid @RequestBody FamilyMemberRequest r){ return ResponseEntity.ok(ApiResponse.ok("가족 구성원을 등록했습니다.", lifeService.createMember(p.userId(), r))); }
    @PutMapping("/family/members/{memberId}") public ResponseEntity<ApiResponse<FamilyMemberResponse>> updateMember(@CurrentUser UserPrincipal p,@PathVariable Long memberId,@Valid @RequestBody FamilyMemberRequest r){ return ResponseEntity.ok(ApiResponse.ok("가족 구성원을 수정했습니다.", lifeService.updateMember(p.userId(), memberId, r))); }
    @DeleteMapping("/family/members/{memberId}") public ResponseEntity<ApiResponse<Void>> deleteMember(@CurrentUser UserPrincipal p,@PathVariable Long memberId){ lifeService.deleteMember(p.userId(), memberId); return ResponseEntity.ok(ApiResponse.ok("가족 구성원을 삭제했습니다.")); }

    @GetMapping("/family/events") public ResponseEntity<ApiResponse<List<FamilyEventResponse>>> events(@CurrentUser UserPrincipal p){ return ResponseEntity.ok(ApiResponse.ok(lifeService.events(p.userId()))); }
    @PostMapping("/family/events") public ResponseEntity<ApiResponse<FamilyEventResponse>> createEvent(@CurrentUser UserPrincipal p,@Valid @RequestBody FamilyEventRequest r){ return ResponseEntity.ok(ApiResponse.ok("가족 행사를 등록했습니다.", lifeService.createEvent(p.userId(), r))); }
    @PutMapping("/family/events/{eventId}") public ResponseEntity<ApiResponse<FamilyEventResponse>> updateEvent(@CurrentUser UserPrincipal p,@PathVariable Long eventId,@Valid @RequestBody FamilyEventRequest r){ return ResponseEntity.ok(ApiResponse.ok("가족 행사를 수정했습니다.", lifeService.updateEvent(p.userId(), eventId, r))); }
    @DeleteMapping("/family/events/{eventId}") public ResponseEntity<ApiResponse<Void>> deleteEvent(@CurrentUser UserPrincipal p,@PathVariable Long eventId){ lifeService.deleteEvent(p.userId(), eventId); return ResponseEntity.ok(ApiResponse.ok("가족 행사를 삭제했습니다.")); }

    @GetMapping("/newhome") public ResponseEntity<ApiResponse<List<NewHomeItemResponse>>> newHomeItems(@CurrentUser UserPrincipal p, @RequestParam(required = false) NewHomeCategory category){ return ResponseEntity.ok(ApiResponse.ok(lifeService.newHomeItems(p.userId(), category))); }
    @PostMapping("/newhome") public ResponseEntity<ApiResponse<NewHomeItemResponse>> createNewHomeItem(@CurrentUser UserPrincipal p, @Valid @RequestBody NewHomeItemRequest r){ return ResponseEntity.ok(ApiResponse.ok("신혼집 체크리스트 항목을 추가했습니다.", lifeService.createNewHomeItem(p.userId(), r))); }
    @PutMapping("/newhome/{itemId}") public ResponseEntity<ApiResponse<NewHomeItemResponse>> updateNewHomeItem(@CurrentUser UserPrincipal p, @PathVariable Long itemId, @Valid @RequestBody NewHomeItemRequest r){ return ResponseEntity.ok(ApiResponse.ok("신혼집 체크리스트 항목을 수정했습니다.", lifeService.updateNewHomeItem(p.userId(), itemId, r))); }
    @DeleteMapping("/newhome/{itemId}") public ResponseEntity<ApiResponse<Void>> deleteNewHomeItem(@CurrentUser UserPrincipal p, @PathVariable Long itemId){ lifeService.deleteNewHomeItem(p.userId(), itemId); return ResponseEntity.ok(ApiResponse.ok("신혼집 체크리스트 항목을 삭제했습니다.")); }
}
