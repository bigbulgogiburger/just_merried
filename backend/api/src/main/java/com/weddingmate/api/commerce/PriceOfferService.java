package com.weddingmate.api.commerce;

import com.weddingmate.api.commerce.dto.PaymentDtos.*;
import com.weddingmate.common.exception.BusinessException;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.commerce.entity.OfferStatus;
import com.weddingmate.domain.commerce.entity.PriceOffer;
import com.weddingmate.domain.commerce.repository.PriceOfferRepository;
import com.weddingmate.domain.market.entity.SecondhandProduct;
import com.weddingmate.domain.market.repository.SecondhandProductRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class PriceOfferService {
    private final PriceOfferRepository priceOfferRepository; private final SecondhandProductRepository secondhandProductRepository; private final UserRepository userRepository;
    @Transactional
    public OfferResponse create(Long userId, OfferCreateRequest request){ User buyer=userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND,"사용자를 찾을 수 없습니다.")); SecondhandProduct p=secondhandProductRepository.findById(request.secondhandProductId()).orElseThrow(() -> new NotFoundException(ErrorCode.SECONDHAND_PRODUCT_NOT_FOUND,"중고 상품을 찾을 수 없습니다.")); PriceOffer o=priceOfferRepository.save(PriceOffer.builder().secondhandProduct(p).buyerUser(buyer).sellerUser(p.getSellerUser()).offeredPrice(request.offeredPrice()).message(request.message()).build()); return OfferResponse.from(o); }
    @Transactional
    public OfferResponse respond(Long userId, Long offerId, OfferRespondRequest request){ PriceOffer o=priceOfferRepository.findById(offerId).orElseThrow(() -> new NotFoundException(ErrorCode.OFFER_NOT_FOUND,"가격 제안을 찾을 수 없습니다.")); if(!o.getSellerUser().getId().equals(userId)) throw new BusinessException(ErrorCode.OFFER_ACCESS_DENIED,"판매자만 응답할 수 있습니다."); if(request.status()!= OfferStatus.ACCEPTED && request.status()!=OfferStatus.REJECTED) throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,"응답 상태는 ACCEPTED 또는 REJECTED여야 합니다."); o.respond(request.status()); return OfferResponse.from(o); }
    @Transactional(readOnly = true)
    public List<OfferResponse> myOffers(Long userId){ return priceOfferRepository.findBySellerUserIdOrBuyerUserIdOrderByCreatedAtDesc(userId,userId).stream().map(OfferResponse::from).toList(); }
}
