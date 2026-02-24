package com.weddingmate.api.commerce;

import com.weddingmate.api.commerce.dto.PaymentDtos.SettlementResponse;
import com.weddingmate.domain.commerce.entity.*;
import com.weddingmate.domain.commerce.repository.SettlementRepository;
import com.weddingmate.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service @RequiredArgsConstructor
public class SettlementService {
    private final SettlementRepository settlementRepository;
    @Transactional
    public Settlement createEscrowSettlement(User seller, Long referenceId, long gross){
        long fee=Math.round(gross*0.035); long net=gross-fee;
        Settlement s=settlementRepository.save(Settlement.builder().sellerUser(seller).settlementType(SettlementType.ESCROW).referenceId(referenceId).grossAmount(gross).feeRate(BigDecimal.valueOf(3.5)).feeAmount(fee).netAmount(net).status(SettlementStatus.PENDING).build());
        s.settle(); return s;
    }
    @Transactional(readOnly = true)
    public List<SettlementResponse> mySettlements(Long userId){ return settlementRepository.findBySellerUserIdOrderByCreatedAtDesc(userId).stream().map(SettlementResponse::from).toList(); }
}
