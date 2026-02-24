package com.weddingmate.api.community;

import com.weddingmate.api.community.dto.FollowUserResponse;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Community Follow", description = "팔로우 API")
@RestController
@RequestMapping("/api/v1/community/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @Operation(summary = "팔로우")
    @PostMapping("/{id}/follow")
    public ResponseEntity<ApiResponse<Void>> follow(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long id
    ) {
        followService.follow(principal.userId(), id);
        return ResponseEntity.ok(ApiResponse.ok("팔로우했습니다."));
    }

    @Operation(summary = "언팔로우")
    @DeleteMapping("/{id}/follow")
    public ResponseEntity<ApiResponse<Void>> unfollow(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long id
    ) {
        followService.unfollow(principal.userId(), id);
        return ResponseEntity.ok(ApiResponse.ok("언팔로우했습니다."));
    }

    @Operation(summary = "팔로워 목록")
    @GetMapping("/{id}/followers")
    public ResponseEntity<ApiResponse<List<FollowUserResponse>>> followers(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(followService.followers(id)));
    }

    @Operation(summary = "팔로잉 목록")
    @GetMapping("/{id}/following")
    public ResponseEntity<ApiResponse<List<FollowUserResponse>>> following(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(followService.following(id)));
    }
}
