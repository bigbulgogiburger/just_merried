package com.weddingmate.domain.life.repository;

import com.weddingmate.domain.life.entity.FamilyEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FamilyEventRepository extends JpaRepository<FamilyEvent, Long> {
    List<FamilyEvent> findByUserIdOrderByEventDateAsc(Long userId);
    List<FamilyEvent> findByUserIdAndEventDateBetweenOrderByEventDateAsc(Long userId, LocalDate from, LocalDate to);
}
