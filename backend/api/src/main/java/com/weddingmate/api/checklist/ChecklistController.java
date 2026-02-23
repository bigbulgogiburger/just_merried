package com.weddingmate.api.checklist;

import com.weddingmate.api.checklist.dto.*;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Checklist", description = "체크리스트 API")
@RestController
@RequestMapping("/api/v1/checklists")
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistService checklistService;

    @Operation(summary = "체크리스트 생성")
    @PostMapping
    public ResponseEntity<ApiResponse<ChecklistResponse>> create(
            @CurrentUser UserPrincipal principal,
            @Valid @RequestBody ChecklistCreateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("체크리스트가 생성되었습니다.", checklistService.createChecklist(principal.userId(), request)));
    }

    @Operation(summary = "내 체크리스트 목록")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ChecklistResponse>>> list(@CurrentUser UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.ok(checklistService.getMyChecklists(principal.userId())));
    }

    @Operation(summary = "체크리스트 수정")
    @PutMapping("/{checklistId}")
    public ResponseEntity<ApiResponse<ChecklistResponse>> update(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long checklistId,
            @RequestBody ChecklistUpdateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("체크리스트가 수정되었습니다.", checklistService.updateChecklist(principal.userId(), checklistId, request)));
    }

    @Operation(summary = "체크리스트 삭제")
    @DeleteMapping("/{checklistId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long checklistId
    ) {
        checklistService.deleteChecklist(principal.userId(), checklistId);
        return ResponseEntity.ok(ApiResponse.ok("체크리스트가 삭제되었습니다."));
    }

    @Operation(summary = "체크리스트 항목 추가")
    @PostMapping("/{checklistId}/items")
    public ResponseEntity<ApiResponse<ChecklistItemResponse>> addItem(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long checklistId,
            @Valid @RequestBody ChecklistItemCreateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("체크리스트 항목이 추가되었습니다.", checklistService.addItem(principal.userId(), checklistId, request)));
    }

    @Operation(summary = "체크리스트 항목 수정")
    @PutMapping("/{checklistId}/items/{itemId}")
    public ResponseEntity<ApiResponse<ChecklistItemResponse>> updateItem(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long checklistId,
            @PathVariable Long itemId,
            @RequestBody ChecklistItemUpdateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("체크리스트 항목이 수정되었습니다.", checklistService.updateItem(principal.userId(), checklistId, itemId, request)));
    }

    @Operation(summary = "체크리스트 항목 완료 토글")
    @PatchMapping("/{checklistId}/items/{itemId}/toggle")
    public ResponseEntity<ApiResponse<ChecklistItemResponse>> toggleItem(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long checklistId,
            @PathVariable Long itemId,
            @RequestBody ChecklistItemToggleRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("체크리스트 항목 상태가 변경되었습니다.", checklistService.toggleItem(principal.userId(), checklistId, itemId, request)));
    }

    @Operation(summary = "체크리스트 항목 삭제")
    @DeleteMapping("/{checklistId}/items/{itemId}")
    public ResponseEntity<ApiResponse<Void>> deleteItem(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long checklistId,
            @PathVariable Long itemId
    ) {
        checklistService.deleteItem(principal.userId(), checklistId, itemId);
        return ResponseEntity.ok(ApiResponse.ok("체크리스트 항목이 삭제되었습니다."));
    }
}
