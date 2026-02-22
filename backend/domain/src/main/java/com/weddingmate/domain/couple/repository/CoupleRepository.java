package com.weddingmate.domain.couple.repository;

import com.weddingmate.domain.couple.entity.Couple;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoupleRepository extends JpaRepository<Couple, Long> {

    Optional<Couple> findByInviteCode(String inviteCode);
}
