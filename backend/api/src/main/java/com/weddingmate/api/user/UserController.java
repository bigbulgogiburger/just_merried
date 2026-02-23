package com.weddingmate.api.user;

import com.weddingmate.api.user.dto.MeResponse;
import com.weddingmate.api.user.dto.UpdateMeRequest;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "내 정보 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @Operation(summary = "내 프로필 조회")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MeResponse>> me(@CurrentUser UserPrincipal principal) {
        User user = userRepository.findById(principal.userId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
        return ResponseEntity.ok(ApiResponse.ok(MeResponse.from(user)));
    }

    @Operation(summary = "내 프로필 수정")
    @PutMapping("/me")
    @Transactional
    public ResponseEntity<ApiResponse<MeResponse>> updateMe(
            @CurrentUser UserPrincipal principal,
            @RequestBody UpdateMeRequest request
    ) {
        User user = userRepository.findById(principal.userId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));

        user.updateProfile(request.nickname(), request.profileImageUrl(), request.region());
        if (request.weddingStatus() != null) {
            user.updateWeddingInfo(request.weddingStatus(), request.weddingDate());
        }

        return ResponseEntity.ok(ApiResponse.ok("프로필이 수정되었습니다.", MeResponse.from(user)));
    }
}
