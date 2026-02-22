package com.weddingmate.domain.couple.repository;

import com.weddingmate.domain.couple.entity.CoupleMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoupleMemberRepository extends JpaRepository<CoupleMember, Long> {

    List<CoupleMember> findByCoupleId(Long coupleId);

    Optional<CoupleMember> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
