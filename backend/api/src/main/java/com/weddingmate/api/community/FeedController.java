package com.weddingmate.api.community;

import com.weddingmate.api.community.dto.FeedItemResponse;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Community Feed", description = "커뮤니티 피드 API")
@RestController
@RequestMapping("/api/v1/community/feed")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @Operation(summary = "전체 피드")
    @GetMapping
    public ResponseEntity<ApiResponse<List<FeedItemResponse>>> globalFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(ApiResponse.ok(feedService.getGlobalFeed(page, size)));
    }

    @Operation(summary = "팔로잉 피드")
    @GetMapping("/following")
    public ResponseEntity<ApiResponse<List<FeedItemResponse>>> followingFeed(
            @CurrentUser UserPrincipal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(ApiResponse.ok(feedService.getFollowingFeed(principal.userId(), page, size)));
    }

    @Operation(summary = "지역 피드")
    @GetMapping("/region")
    public ResponseEntity<ApiResponse<List<FeedItemResponse>>> regionFeed(
            @RequestParam String region,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(ApiResponse.ok(feedService.getRegionFeed(region, page, size)));
    }
}
