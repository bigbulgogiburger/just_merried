package com.weddingmate.api.commerce;

import com.weddingmate.api.commerce.dto.PaymentDtos.*;
import com.weddingmate.common.exception.BusinessException;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.commerce.entity.EscrowStatus;
import com.weddingmate.domain.commerce.entity.EscrowTransaction;
import com.weddingmate.domain.commerce.repository.EscrowTransactionRepository;
import com.weddingmate.domain.market.entity.SecondhandProduct;
import com.weddingmate.domain.market.repository.SecondhandProductRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class EscrowService {
    private final EscrowTransactionRepository escrowTransactionRepository; private final SecondhandProductRepository secondhandProductRepository; private final UserRepository userRepository; private final SettlementService settlementService;
    @Transactional
    public EscrowResponse create(Long userId, EscrowCreateRequest request){
        User buyer=userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND,"사용자를 찾을 수 없습니다."));
        SecondhandProduct p=secondhandProductRepository.findById(request.secondhandProductId()).orElseThrow(() -> new NotFoundException(ErrorCode.SECONDHAND_PRODUCT_NOT_FOUND,"중고 상품을 찾을 수 없습니다."));
        EscrowTransaction e=escrowTransactionRepository.save(EscrowTransaction.builder().secondhandProduct(p).buyerUser(buyer).sellerUser(p.getSellerUser()).amount(request.amount()).feeAmount(Math.round(request.amount()*0.035)).status(EscrowStatus.PAID).build());
        return EscrowResponse.from(e);
    }
    @Transactional
    public EscrowResponse ship(Long userId, Long id, EscrowShipRequest request){ EscrowTransaction e=ownedSeller(userId,id); e.markShipped(request.carrier(), request.trackingNumber()); return EscrowResponse.from(e); }
    @Transactional
    public EscrowResponse confirm(Long userId, Long id){ EscrowTransaction e=ownedBuyer(userId,id); e.confirm(false); settlementService.createEscrowSettlement(e.getSellerUser(), e.getId(), e.getAmount()); e.settled(); return EscrowResponse.from(e); }
    @Transactional(readOnly = true)
    public List<EscrowResponse> myEscrow(Long userId){ return escrowTransactionRepository.findByBuyerUserIdOrSellerUserIdOrderByCreatedAtDesc(userId,userId).stream().map(EscrowResponse::from).toList(); }
    @Transactional
    public int autoConfirmDueEscrow(){ var list=escrowTransactionRepository.findByStatusAndAutoConfirmDueAtBefore(EscrowStatus.SHIPPED, LocalDateTime.now()); list.forEach(e->{ e.confirm(true); settlementService.createEscrowSettlement(e.getSellerUser(), e.getId(), e.getAmount()); e.settled();}); return list.size(); }
    private EscrowTransaction ownedBuyer(Long userId, Long id){ EscrowTransaction e=get(id); if(!e.getBuyerUser().getId().equals(userId)) throw new BusinessException(ErrorCode.ESCROW_ACCESS_DENIED, "구매자만 수령 확인할 수 있습니다."); return e; }
    private EscrowTransaction ownedSeller(Long userId, Long id){ EscrowTransaction e=get(id); if(!e.getSellerUser().getId().equals(userId)) throw new BusinessException(ErrorCode.ESCROW_ACCESS_DENIED, "판매자만 발송 등록할 수 있습니다."); return e; }
    private EscrowTransaction get(Long id){ return escrowTransactionRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.ESCROW_NOT_FOUND,"에스크로 거래를 찾을 수 없습니다.")); }
}
