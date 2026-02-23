package com.weddingmate.api.vendor;

import com.weddingmate.api.vendor.dto.VendorCreateRequest;
import com.weddingmate.api.vendor.dto.VendorDetailResponse;
import com.weddingmate.api.vendor.dto.VendorListResponse;
import com.weddingmate.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Vendor", description = "업체 탐색 API")
@RestController
@RequestMapping("/api/v1/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @Operation(summary = "업체 목록")
    @GetMapping
    public ResponseEntity<ApiResponse<List<VendorListResponse>>> list(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false, defaultValue = "latest") String sort
    ) {
        return ResponseEntity.ok(ApiResponse.ok(vendorService.list(categoryId, region, minPrice, maxPrice, sort)));
    }

    @Operation(summary = "업체 상세")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VendorDetailResponse>> detail(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(vendorService.detail(id)));
    }

    @Operation(summary = "업체 등록 (관리용 최소)")
    @PostMapping
    public ResponseEntity<ApiResponse<VendorDetailResponse>> create(@Valid @RequestBody VendorCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("업체가 등록되었습니다.", vendorService.create(request)));
    }
}
