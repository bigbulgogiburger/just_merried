package com.weddingmate.api.market;

import com.weddingmate.api.market.dto.*;
import com.weddingmate.common.exception.BusinessException;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.market.entity.*;
import com.weddingmate.domain.market.repository.SecondhandProductImageRepository;
import com.weddingmate.domain.market.repository.SecondhandProductRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecondhandCommandService {
    private final SecondhandProductRepository secondhandProductRepository;
    private final SecondhandProductImageRepository secondhandProductImageRepository;
    private final UserRepository userRepository;

    @Transactional
    public SecondhandDetailResponse create(Long userId, SecondhandCreateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
        SecondhandProduct product = secondhandProductRepository.save(SecondhandProduct.builder().sellerUser(user).title(request.title()).description(request.description()).price(request.price()).region(request.region()).conditionStatus(request.conditionStatus()).tradeMethod(request.tradeMethod()).saleStatus(SecondhandSaleStatus.ON_SALE).build());
        return SecondhandDetailResponse.of(product, saveImages(product, request.imageUrls()));
    }

    @Transactional
    public SecondhandDetailResponse update(Long userId, Long productId, SecondhandUpdateRequest request) {
        SecondhandProduct product = getOwnedProduct(userId, productId);
        product.update(request.title(), request.description(), request.price(), request.region(), request.conditionStatus(), request.tradeMethod());
        secondhandProductImageRepository.deleteAll(secondhandProductImageRepository.findBySecondhandProductIdOrderBySortOrderAsc(productId));
        return SecondhandDetailResponse.of(product, saveImages(product, request.imageUrls()));
    }

    @Transactional
    public void delete(Long userId, Long productId) { secondhandProductRepository.delete(getOwnedProduct(userId, productId)); }

    @Transactional
    public SecondhandDetailResponse changeStatus(Long userId, Long productId, SecondhandSaleStatusUpdateRequest request) {
        SecondhandProduct product = getOwnedProduct(userId, productId); product.changeSaleStatus(request.saleStatus());
        return SecondhandDetailResponse.of(product, secondhandProductImageRepository.findBySecondhandProductIdOrderBySortOrderAsc(productId).stream().map(SecondhandImageResponse::from).toList());
    }

    @Transactional(readOnly = true)
    public List<SecondhandSummaryResponse> myProducts(Long userId) {
        return secondhandProductRepository.findBySellerUserIdOrderByCreatedAtDesc(userId).stream().map(p -> SecondhandSummaryResponse.of(p, secondhandProductImageRepository.findBySecondhandProductIdOrderBySortOrderAsc(p.getId()).stream().findFirst().map(SecondhandProductImage::getImageUrl).orElse(null))).toList();
    }

    private SecondhandProduct getOwnedProduct(Long userId, Long productId) {
        SecondhandProduct product = secondhandProductRepository.findById(productId).orElseThrow(() -> new NotFoundException(ErrorCode.SECONDHAND_PRODUCT_NOT_FOUND, "중고 상품을 찾을 수 없습니다."));
        if (!product.getSellerUser().getId().equals(userId)) throw new BusinessException(ErrorCode.SECONDHAND_PRODUCT_ACCESS_DENIED, "작성자만 가능합니다.");
        return product;
    }

    private List<SecondhandImageResponse> saveImages(SecondhandProduct product, List<String> imageUrls) {
        List<SecondhandImageResponse> result = new ArrayList<>();
        if (imageUrls == null) return result;
        for (int i = 0; i < imageUrls.size(); i++) {
            var image = secondhandProductImageRepository.save(SecondhandProductImage.builder().secondhandProduct(product).imageUrl(imageUrls.get(i)).sortOrder(i).build());
            result.add(SecondhandImageResponse.from(image));
        }
        return result;
    }
}
