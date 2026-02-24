package com.weddingmate.api.community;

import com.weddingmate.api.community.dto.CommunitySearchResponse;
import com.weddingmate.api.community.dto.HashtagResponse;
import com.weddingmate.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Community Discovery", description = "해시태그/탐색 API")
@RestController
@RequestMapping("/api/v1/community")
@RequiredArgsConstructor
public class DiscoveryController {

    private final DiscoveryService discoveryService;

    @Operation(summary = "인기 해시태그")
    @GetMapping("/hashtags/trending")
    public ResponseEntity<ApiResponse<List<HashtagResponse>>> trending(
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ApiResponse.ok(discoveryService.trendingHashtags(size)));
    }

    @Operation(summary = "커뮤니티 검색")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<CommunitySearchResponse>> search(
            @RequestParam String q,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(ApiResponse.ok(discoveryService.search(q, size)));
    }
}
