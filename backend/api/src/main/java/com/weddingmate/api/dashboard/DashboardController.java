package com.weddingmate.api.dashboard;

import com.weddingmate.api.dashboard.dto.DashboardSummaryResponse;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.entity.WeddingStatus;
import com.weddingmate.domain.user.repository.UserRepository;
import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Tag(name = "Dashboard", description = "홈 대시보드 API")
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserRepository userRepository;

    @Operation(summary = "대시보드 요약 조회")
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryResponse>> summary(@CurrentUser UserPrincipal principal) {
        User user = userRepository.findById(principal.userId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));

        LocalDate today = LocalDate.now();
        LocalDate weddingDate = user.getWeddingDate();
        long dDay = weddingDate == null ? 0 : ChronoUnit.DAYS.between(today, weddingDate);

        int progress = switch (user.getWeddingStatus()) {
            case PREPARING -> 25;
            case IN_PROGRESS -> 60;
            case COMPLETED -> 100;
        };

        DashboardSummaryResponse response = new DashboardSummaryResponse(
                weddingDate,
                dDay,
                progress,
                Math.max(1, 5 - (progress / 20)),
                progress / 10
        );

        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
