package com.weddingmate.api.schedule;

import com.weddingmate.api.schedule.dto.ScheduleCreateRequest;
import com.weddingmate.api.schedule.dto.ScheduleResponse;
import com.weddingmate.api.schedule.dto.ScheduleUpdateRequest;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Schedule", description = "일정 관리 API")
@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "일정 생성")
    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleResponse>> create(
            @CurrentUser UserPrincipal principal,
            @Valid @RequestBody ScheduleCreateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("일정이 생성되었습니다.", scheduleService.create(principal.userId(), request)));
    }

    @Operation(summary = "기간별 일정 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ScheduleResponse>>> list(
            @CurrentUser UserPrincipal principal,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return ResponseEntity.ok(ApiResponse.ok(scheduleService.findByRange(principal.userId(), from, to)));
    }

    @Operation(summary = "일정 수정")
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse<ScheduleResponse>> update(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long scheduleId,
            @RequestBody ScheduleUpdateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("일정이 수정되었습니다.", scheduleService.update(principal.userId(), scheduleId, request)));
    }

    @Operation(summary = "일정 삭제")
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long scheduleId
    ) {
        scheduleService.delete(principal.userId(), scheduleId);
        return ResponseEntity.ok(ApiResponse.ok("일정이 삭제되었습니다."));
    }
}
