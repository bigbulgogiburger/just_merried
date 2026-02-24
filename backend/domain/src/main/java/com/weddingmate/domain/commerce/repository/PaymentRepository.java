package com.weddingmate.domain.commerce.repository;

import com.weddingmate.domain.commerce.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
