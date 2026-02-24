package com.weddingmate.api.community.notification;

import com.weddingmate.api.community.notification.dto.NotificationResponse;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Community Notification", description = "알림 API")
@RestController
@RequestMapping("/api/v1/community/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 목록")
    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> list(@CurrentUser UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.ok(notificationService.list(principal.userId())));
    }

    @Operation(summary = "알림 읽음 처리")
    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> read(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long id
    ) {
        notificationService.markRead(principal.userId(), id);
        return ResponseEntity.ok(ApiResponse.ok("알림을 읽음 처리했습니다."));
    }

    @Operation(summary = "알림 전체 읽음")
    @PatchMapping("/read-all")
    public ResponseEntity<ApiResponse<Void>> readAll(@CurrentUser UserPrincipal principal) {
        notificationService.markAllRead(principal.userId());
        return ResponseEntity.ok(ApiResponse.ok("모든 알림을 읽음 처리했습니다."));
    }
}
