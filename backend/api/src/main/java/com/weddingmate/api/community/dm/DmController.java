package com.weddingmate.api.community.dm;

import com.weddingmate.api.community.dm.dto.DmMessageCreateRequest;
import com.weddingmate.api.community.dm.dto.DmMessageResponse;
import com.weddingmate.api.community.dm.dto.DmRoomCreateRequest;
import com.weddingmate.api.community.dm.dto.DmRoomResponse;
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

@Tag(name = "Community DM", description = "DM API")
@RestController
@RequestMapping("/api/v1/community/dm")
@RequiredArgsConstructor
public class DmController {

    private final DmService dmService;

    @Operation(summary = "DM 방 생성/재사용")
    @PostMapping("/rooms")
    public ResponseEntity<ApiResponse<DmRoomResponse>> createRoom(
            @CurrentUser UserPrincipal principal,
            @Valid @RequestBody DmRoomCreateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("DM 방 준비 완료", dmService.createOrReuseRoom(principal.userId(), request)));
    }

    @Operation(summary = "내 DM 방 목록")
    @GetMapping("/rooms")
    public ResponseEntity<ApiResponse<List<DmRoomResponse>>> myRooms(@CurrentUser UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.ok(dmService.myRooms(principal.userId())));
    }

    @Operation(summary = "DM 메시지 목록")
    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<ApiResponse<List<DmMessageResponse>>> messages(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long roomId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(dmService.messages(principal.userId(), roomId)));
    }

    @Operation(summary = "DM 메시지 전송")
    @PostMapping("/rooms/{roomId}/messages")
    public ResponseEntity<ApiResponse<DmMessageResponse>> send(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long roomId,
            @Valid @RequestBody DmMessageCreateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("메시지를 전송했습니다.", dmService.sendMessage(principal.userId(), roomId, request)));
    }
}
