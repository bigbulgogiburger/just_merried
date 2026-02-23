package com.weddingmate.api.admin;

import com.weddingmate.api.business.BusinessService;
import com.weddingmate.api.business.dto.BusinessSummaryResponse;
import com.weddingmate.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Admin Business", description = "관리자 비즈니스 심사 API")
@RestController
@RequestMapping("/api/v1/admin/business")
@RequiredArgsConstructor
public class AdminBusinessController {

    private final BusinessService businessService;

    @Operation(summary = "심사 대기 목록")
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<BusinessSummaryResponse>>> pending() {
        return ResponseEntity.ok(ApiResponse.ok(businessService.pending()));
    }

    @Operation(summary = "비즈니스 승인")
    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<BusinessSummaryResponse>> approve(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("승인 완료", businessService.approve(id)));
    }
}
