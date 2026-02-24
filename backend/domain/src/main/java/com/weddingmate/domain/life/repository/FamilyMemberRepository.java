package com.weddingmate.domain.life.repository;

import com.weddingmate.domain.life.entity.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
    List<FamilyMember> findByUserIdOrderByCreatedAtDesc(Long userId);
}
