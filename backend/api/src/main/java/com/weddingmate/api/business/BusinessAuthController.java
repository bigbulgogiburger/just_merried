package com.weddingmate.api.business;

import com.weddingmate.api.business.dto.BusinessRegisterRequest;
import com.weddingmate.api.business.dto.BusinessSummaryResponse;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Business", description = "비즈니스 회원 API")
@RestController
@RequestMapping("/api/v1/auth/business")
@RequiredArgsConstructor
public class BusinessAuthController {

    private final BusinessService businessService;

    @Operation(summary = "비즈니스 회원 등록")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<BusinessSummaryResponse>> register(
            @CurrentUser UserPrincipal principal,
            @Valid @RequestBody BusinessRegisterRequest request
    ) {
        BusinessSummaryResponse response = businessService.register(principal.userId(), request);
        return ResponseEntity.ok(ApiResponse.ok("사업자 등록 신청이 완료되었습니다.", response));
    }
}
