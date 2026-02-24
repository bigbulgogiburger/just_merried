package com.weddingmate.api.community;

import com.weddingmate.api.community.dto.CommentCreateRequest;
import com.weddingmate.api.community.dto.CommentResponse;
import com.weddingmate.common.response.ApiResponse;
import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Community Interaction", description = "좋아요/댓글 API")
@RestController
@RequestMapping("/api/v1/community")
@RequiredArgsConstructor
public class InteractionController {

    private final InteractionService interactionService;

    @Operation(summary = "좋아요 추가")
    @PostMapping("/posts/{id}/like")
    public ResponseEntity<ApiResponse<Void>> like(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long id
    ) {
        interactionService.like(principal.userId(), id);
        return ResponseEntity.ok(ApiResponse.ok("좋아요가 반영되었습니다."));
    }

    @Operation(summary = "좋아요 해제")
    @DeleteMapping("/posts/{id}/like")
    public ResponseEntity<ApiResponse<Void>> unlike(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long id
    ) {
        interactionService.unlike(principal.userId(), id);
        return ResponseEntity.ok(ApiResponse.ok("좋아요가 해제되었습니다."));
    }

    @Operation(summary = "댓글 등록")
    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long id,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok("댓글이 등록되었습니다.", interactionService.createComment(principal.userId(), id, request)));
    }

    @Operation(summary = "댓글 목록")
    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> comments(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(interactionService.getComments(id)));
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @CurrentUser UserPrincipal principal,
            @PathVariable Long id
    ) {
        interactionService.deleteComment(principal.userId(), id);
        return ResponseEntity.ok(ApiResponse.ok("댓글이 삭제되었습니다."));
    }
}
