package com.weddingmate.domain.commerce.repository;

import com.weddingmate.domain.commerce.entity.TradeReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeReviewRepository extends JpaRepository<TradeReview, Long> {
    List<TradeReview> findByRevieweeUserIdOrderByCreatedAtDesc(Long revieweeUserId);
}
