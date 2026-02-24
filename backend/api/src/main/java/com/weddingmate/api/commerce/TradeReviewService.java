package com.weddingmate.api.commerce;

import com.weddingmate.api.commerce.dto.PaymentDtos.*;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.commerce.entity.EscrowTransaction;
import com.weddingmate.domain.commerce.entity.TradeReview;
import com.weddingmate.domain.commerce.repository.EscrowTransactionRepository;
import com.weddingmate.domain.commerce.repository.TradeReviewRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class TradeReviewService {
    private final TradeReviewRepository tradeReviewRepository; private final EscrowTransactionRepository escrowTransactionRepository; private final UserRepository userRepository;
    @Transactional
    public TradeReviewResponse create(Long userId, TradeReviewCreateRequest request){ EscrowTransaction e=escrowTransactionRepository.findById(request.escrowTransactionId()).orElseThrow(() -> new NotFoundException(ErrorCode.ESCROW_NOT_FOUND,"에스크로 거래를 찾을 수 없습니다.")); User reviewer=userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND,"사용자를 찾을 수 없습니다.")); User reviewee=userRepository.findById(request.revieweeUserId()).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND,"사용자를 찾을 수 없습니다.")); TradeReview r=tradeReviewRepository.save(TradeReview.builder().escrowTransaction(e).reviewerUser(reviewer).revieweeUser(reviewee).scoreKindness(request.scoreKindness()).scorePunctuality(request.scorePunctuality()).scoreQuality(request.scoreQuality()).content(request.content()).build()); applyManner(reviewee, r.averageScore()); return TradeReviewResponse.from(r); }
    @Transactional(readOnly = true)
    public List<TradeReviewResponse> reviews(Long userId){ return tradeReviewRepository.findByRevieweeUserIdOrderByCreatedAtDesc(userId).stream().map(TradeReviewResponse::from).toList(); }
    private void applyManner(User reviewee, double average){ double delta=(average-3.0)*0.2; reviewee.updateMannerTemperature(reviewee.getMannerTemperature()+delta); }
}
