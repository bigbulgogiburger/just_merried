package com.weddingmate.api.commerce;

import com.weddingmate.api.commerce.dto.PaymentDtos.*;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.commerce.entity.Subscription;
import com.weddingmate.domain.commerce.entity.SubscriptionPlanType;
import com.weddingmate.domain.commerce.repository.SubscriptionRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository; private final UserRepository userRepository; private final PaymentService paymentService;
    @Transactional
    public SubscriptionResponse subscribe(Long userId, SubscriptionRequest request){ User u=userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND,"사용자를 찾을 수 없습니다.")); var p=paymentService.findApproved(request.paymentId()); Subscription s=subscriptionRepository.findTopByUserIdOrderByCreatedAtDesc(userId).orElseGet(() -> subscriptionRepository.save(Subscription.builder().user(u).planType(SubscriptionPlanType.FREE).autoRenew(false).build())); if(request.planType()==SubscriptionPlanType.BASIC) s.activateBasic(p); return SubscriptionResponse.from(s); }
    @Transactional(readOnly = true)
    public SubscriptionResponse my(Long userId){ return SubscriptionResponse.from(subscriptionRepository.findTopByUserIdOrderByCreatedAtDesc(userId).orElseThrow(() -> new NotFoundException(ErrorCode.SUBSCRIPTION_NOT_FOUND,"구독 정보를 찾을 수 없습니다."))); }
    @Transactional
    public SubscriptionResponse cancel(Long userId){ Subscription s=subscriptionRepository.findTopByUserIdOrderByCreatedAtDesc(userId).orElseThrow(() -> new NotFoundException(ErrorCode.SUBSCRIPTION_NOT_FOUND,"구독 정보를 찾을 수 없습니다.")); s.cancel(); return SubscriptionResponse.from(s); }
}
