package com.weddingmate.domain.commerce.repository;

import com.weddingmate.domain.commerce.entity.EscrowStatus;
import com.weddingmate.domain.commerce.entity.EscrowTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EscrowTransactionRepository extends JpaRepository<EscrowTransaction, Long> {
    List<EscrowTransaction> findByBuyerUserIdOrSellerUserIdOrderByCreatedAtDesc(Long buyerUserId, Long sellerUserId);
    List<EscrowTransaction> findByStatusAndAutoConfirmDueAtBefore(EscrowStatus status, LocalDateTime dueAt);
}
