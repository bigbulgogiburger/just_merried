package com.weddingmate.api.community;

import com.weddingmate.api.community.dto.PostCreateRequest;
import com.weddingmate.api.community.dto.PostDetailResponse;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Community Post", description = "커뮤니티 게시글 API")
@RestController
@RequestMapping("/api/v1/community/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 작성")
    @PostMapping
    public ResponseEntity<ApiResponse<PostDetailResponse>> create(
            @CurrentUser UserPrincipal principal,
            @Valid @RequestBody PostCreateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("게시글이 등록되었습니다.", postService.create(principal.userId(), request)));
    }

    @Operation(summary = "게시글 상세")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDetailResponse>> detail(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(postService.detail(id)));
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long id
    ) {
        postService.delete(principal.userId(), id);
        return ResponseEntity.ok(ApiResponse.ok("게시글이 삭제되었습니다."));
    }
}
