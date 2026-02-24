package com.weddingmate.api.market;

import com.weddingmate.api.market.dto.*;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.market.entity.*;
import com.weddingmate.domain.market.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketQueryService {
    private final MarketCategoryRepository marketCategoryRepository;
    private final MarketProductRepository marketProductRepository;
    private final MarketProductImageRepository marketProductImageRepository;
    private final MarketProductOptionRepository marketProductOptionRepository;
    private final SecondhandProductRepository secondhandProductRepository;
    private final SecondhandProductImageRepository secondhandProductImageRepository;

    @Transactional(readOnly = true)
    public List<MarketCategoryResponse> categories() { return marketCategoryRepository.findAllByOrderBySortOrderAsc().stream().map(MarketCategoryResponse::from).toList(); }

    @Transactional(readOnly = true)
    public List<MarketProductSummaryResponse> marketProducts(Long categoryId, String sort, int page, int size) {
        var pageable = PageRequest.of(page, size);
        List<MarketProduct> products = categoryId == null ? marketProductRepository.findByStatusOrderByCreatedAtDesc(MarketProductStatus.ACTIVE, pageable) : marketProductRepository.findByCategoryIdAndStatusOrderByCreatedAtDesc(categoryId, MarketProductStatus.ACTIVE, pageable);
        return products.stream().map(p -> MarketProductSummaryResponse.of(p, firstMarketImage(p.getId()))).toList();
    }

    @Transactional(readOnly = true)
    public MarketProductDetailResponse marketProductDetail(Long productId) {
        MarketProduct product = marketProductRepository.findById(productId).orElseThrow(() -> new NotFoundException(ErrorCode.MARKET_PRODUCT_NOT_FOUND, "상품을 찾을 수 없습니다."));
        var images = marketProductImageRepository.findByProductIdOrderBySortOrderAsc(productId).stream().map(MarketProductImageResponse::from).toList();
        var options = marketProductOptionRepository.findByProductIdOrderByCreatedAtAsc(productId).stream().map(MarketProductOptionResponse::from).toList();
        return MarketProductDetailResponse.of(product, images, options);
    }

    @Transactional(readOnly = true)
    public List<SecondhandSummaryResponse> secondhandProducts(String region, int page, int size) {
        var pageable = PageRequest.of(page, size);
        List<SecondhandProduct> products = (region == null || region.isBlank()) ? secondhandProductRepository.findBySaleStatusOrderByCreatedAtDesc(SecondhandSaleStatus.ON_SALE, pageable) : secondhandProductRepository.findByRegionAndSaleStatusOrderByCreatedAtDesc(region, SecondhandSaleStatus.ON_SALE, pageable);
        return products.stream().map(p -> SecondhandSummaryResponse.of(p, firstSecondhandImage(p.getId()))).toList();
    }

    @Transactional(readOnly = true)
    public SecondhandDetailResponse secondhandDetail(Long productId) {
        SecondhandProduct product = secondhandProductRepository.findById(productId).orElseThrow(() -> new NotFoundException(ErrorCode.SECONDHAND_PRODUCT_NOT_FOUND, "중고 상품을 찾을 수 없습니다."));
        var images = secondhandProductImageRepository.findBySecondhandProductIdOrderBySortOrderAsc(productId).stream().map(SecondhandImageResponse::from).toList();
        return SecondhandDetailResponse.of(product, images);
    }

    @Transactional(readOnly = true)
    public MarketSearchResponse search(String keyword, String region, Long minPrice, Long maxPrice, int page, int size) {
        var pageable = PageRequest.of(page, size);
        var marketLatest = marketProductRepository.searchByKeyword(keyword, MarketProductStatus.ACTIVE, pageable).stream().filter(i -> inPriceRange(i.getBasePrice(), minPrice, maxPrice)).map(i -> MarketProductSummaryResponse.of(i, firstMarketImage(i.getId()))).toList();
        var secondLatest = secondhandProductRepository.searchByKeyword(keyword, SecondhandSaleStatus.ON_SALE, pageable).stream().filter(i -> (region == null || region.isBlank() || region.equals(i.getRegion()))).filter(i -> inPriceRange(i.getPrice(), minPrice, maxPrice)).map(i -> SecondhandSummaryResponse.of(i, firstSecondhandImage(i.getId()))).toList();
        return new MarketSearchResponse(marketLatest, secondLatest, marketLatest, secondLatest);
    }

    private boolean inPriceRange(long price, Long minPrice, Long maxPrice) { return (minPrice == null || price >= minPrice) && (maxPrice == null || price <= maxPrice); }
    private String firstMarketImage(Long productId) { return marketProductImageRepository.findByProductIdOrderBySortOrderAsc(productId).stream().findFirst().map(MarketProductImage::getImageUrl).orElse(null); }
    private String firstSecondhandImage(Long productId) { return secondhandProductImageRepository.findBySecondhandProductIdOrderBySortOrderAsc(productId).stream().findFirst().map(SecondhandProductImage::getImageUrl).orElse(null); }
}
