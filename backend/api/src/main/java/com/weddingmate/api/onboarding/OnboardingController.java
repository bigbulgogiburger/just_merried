package com.weddingmate.api.onboarding;

import com.weddingmate.api.onboarding.dto.OnboardingRequest;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Onboarding", description = "온보딩 API")
@RestController
@RequestMapping("/api/v1/auth/onboarding")
@RequiredArgsConstructor
public class OnboardingController {

    private final UserRepository userRepository;

    @Operation(summary = "온보딩 저장", description = "결혼 상태/예정일/지역 정보를 저장합니다.")
    @PostMapping
    @Transactional
    public ResponseEntity<ApiResponse<Void>> save(
            @CurrentUser UserPrincipal principal,
            @Valid @RequestBody OnboardingRequest request
    ) {
        User user = userRepository.findById(principal.userId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));

        user.updateWeddingInfo(request.weddingStatus(), request.weddingDate());
        user.updateProfile(null, null, request.region());

        return ResponseEntity.ok(ApiResponse.ok("온보딩이 저장되었습니다."));
    }
}
