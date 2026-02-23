package com.weddingmate.api.couple;

import com.weddingmate.api.couple.dto.CoupleConnectRequest;
import com.weddingmate.api.couple.dto.CoupleInviteResponse;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Couple", description = "커플 연동 API")
@RestController
@RequestMapping("/api/v1/auth/couple")
@RequiredArgsConstructor
public class CoupleController {

    private final CoupleService coupleService;

    @Operation(summary = "초대 코드 생성")
    @PostMapping("/invite")
    public ResponseEntity<ApiResponse<CoupleInviteResponse>> invite(@CurrentUser UserPrincipal principal) {
        return ResponseEntity.ok(ApiResponse.ok("초대 코드가 생성되었습니다.", coupleService.createInvite(principal.userId())));
    }

    @Operation(summary = "초대 코드로 커플 연동")
    @PostMapping("/connect")
    public ResponseEntity<ApiResponse<Void>> connect(
            @CurrentUser UserPrincipal principal,
            @Valid @RequestBody CoupleConnectRequest request
    ) {
        coupleService.connect(principal.userId(), request);
        return ResponseEntity.ok(ApiResponse.ok("커플 연동이 완료되었습니다."));
    }

    @Operation(summary = "커플 연동 해제")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> disconnect(@CurrentUser UserPrincipal principal) {
        coupleService.disconnect(principal.userId());
        return ResponseEntity.ok(ApiResponse.ok("커플 연동이 해제되었습니다."));
    }
}
