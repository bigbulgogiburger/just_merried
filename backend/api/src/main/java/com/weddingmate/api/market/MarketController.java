package com.weddingmate.api.market;

import com.weddingmate.api.market.dto.*;
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

@Tag(name = "Market", description = "마켓/중고 API")
@RestController
@RequestMapping("/api/v1/market")
@RequiredArgsConstructor
public class MarketController {
    private final MarketQueryService marketQueryService;
    private final SecondhandCommandService secondhandCommandService;
    private final WishlistService wishlistService;

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<MarketCategoryResponse>>> categories() { return ResponseEntity.ok(ApiResponse.ok(marketQueryService.categories())); }
    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<MarketProductSummaryResponse>>> products(@RequestParam(required = false) Long categoryId,@RequestParam(defaultValue = "latest") String sort,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "20") int size) { return ResponseEntity.ok(ApiResponse.ok(marketQueryService.marketProducts(categoryId, sort, page, size))); }
    @GetMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<MarketProductDetailResponse>> productDetail(@PathVariable Long productId) { return ResponseEntity.ok(ApiResponse.ok(marketQueryService.marketProductDetail(productId))); }
    @PostMapping("/secondhand")
    public ResponseEntity<ApiResponse<SecondhandDetailResponse>> createSecondhand(@CurrentUser UserPrincipal principal,@Valid @RequestBody SecondhandCreateRequest request) { return ResponseEntity.ok(ApiResponse.ok("중고 상품을 등록했습니다.", secondhandCommandService.create(principal.userId(), request))); }
    @GetMapping("/secondhand")
    public ResponseEntity<ApiResponse<List<SecondhandSummaryResponse>>> secondhandList(@RequestParam(required = false) String region,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "20") int size) { return ResponseEntity.ok(ApiResponse.ok(marketQueryService.secondhandProducts(region, page, size))); }
    @GetMapping("/secondhand/{productId}")
    public ResponseEntity<ApiResponse<SecondhandDetailResponse>> secondhandDetail(@PathVariable Long productId) { return ResponseEntity.ok(ApiResponse.ok(marketQueryService.secondhandDetail(productId))); }
    @PutMapping("/secondhand/{productId}")
    public ResponseEntity<ApiResponse<SecondhandDetailResponse>> updateSecondhand(@CurrentUser UserPrincipal principal,@PathVariable Long productId,@Valid @RequestBody SecondhandUpdateRequest request) { return ResponseEntity.ok(ApiResponse.ok("중고 상품을 수정했습니다.", secondhandCommandService.update(principal.userId(), productId, request))); }
    @DeleteMapping("/secondhand/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteSecondhand(@CurrentUser UserPrincipal principal,@PathVariable Long productId) { secondhandCommandService.delete(principal.userId(), productId); return ResponseEntity.ok(ApiResponse.ok("중고 상품을 삭제했습니다.")); }
    @PatchMapping("/secondhand/{productId}/status")
    public ResponseEntity<ApiResponse<SecondhandDetailResponse>> updateSaleStatus(@CurrentUser UserPrincipal principal,@PathVariable Long productId,@Valid @RequestBody SecondhandSaleStatusUpdateRequest request) { return ResponseEntity.ok(ApiResponse.ok("판매 상태를 변경했습니다.", secondhandCommandService.changeStatus(principal.userId(), productId, request))); }
    @GetMapping("/secondhand/me")
    public ResponseEntity<ApiResponse<List<SecondhandSummaryResponse>>> mySecondhand(@CurrentUser UserPrincipal principal) { return ResponseEntity.ok(ApiResponse.ok(secondhandCommandService.myProducts(principal.userId()))); }
    @PostMapping("/wishlist")
    public ResponseEntity<ApiResponse<WishlistItemResponse>> addWishlist(@CurrentUser UserPrincipal principal,@Valid @RequestBody WishlistCreateRequest request) { return ResponseEntity.ok(ApiResponse.ok("관심 목록에 추가했습니다.", wishlistService.add(principal.userId(), request))); }
    @GetMapping("/wishlist")
    public ResponseEntity<ApiResponse<List<WishlistItemResponse>>> myWishlist(@CurrentUser UserPrincipal principal) { return ResponseEntity.ok(ApiResponse.ok(wishlistService.myWishlist(principal.userId()))); }
    @DeleteMapping("/wishlist")
    public ResponseEntity<ApiResponse<Void>> removeWishlist(@CurrentUser UserPrincipal principal,@Valid @RequestBody WishlistCreateRequest request) { wishlistService.remove(principal.userId(), request); return ResponseEntity.ok(ApiResponse.ok("관심 목록에서 제거했습니다.")); }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<MarketSearchResponse>> search(@RequestParam String keyword,@RequestParam(required = false) String region,@RequestParam(required = false) Long minPrice,@RequestParam(required = false) Long maxPrice,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) { return ResponseEntity.ok(ApiResponse.ok(marketQueryService.search(keyword, region, minPrice, maxPrice, page, size))); }
}
