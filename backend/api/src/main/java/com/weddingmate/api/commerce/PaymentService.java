package com.weddingmate.api.commerce;

import com.weddingmate.api.commerce.dto.PaymentDtos.*;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.commerce.entity.Payment;
import com.weddingmate.domain.commerce.entity.PaymentStatus;
import com.weddingmate.domain.commerce.repository.PaymentRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository; private final UserRepository userRepository;
    @Transactional
    public PaymentResponse request(Long userId, PaymentRequest request){ User u=userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
        Payment p=paymentRepository.save(Payment.builder().user(u).orderType(request.orderType()).orderId(request.orderId()).amount(request.amount()).paymentMethod(request.paymentMethod()).provider(request.provider()==null?"MOCK":request.provider()).status(PaymentStatus.PENDING).build());
        p.approve("mock-"+p.getId());
        return PaymentResponse.from(p);
    }
    @Transactional
    public PaymentResponse cancel(Long paymentId){ Payment p=paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException(ErrorCode.PAYMENT_NOT_FOUND, "결제 정보를 찾을 수 없습니다.")); p.cancel(); return PaymentResponse.from(p); }
    @Transactional(readOnly = true)
    public Payment findApproved(Long paymentId){ Payment p=paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException(ErrorCode.PAYMENT_NOT_FOUND, "결제 정보를 찾을 수 없습니다.")); return p; }
}
