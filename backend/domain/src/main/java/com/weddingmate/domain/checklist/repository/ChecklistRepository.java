package com.weddingmate.domain.checklist.repository;

import com.weddingmate.domain.checklist.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    List<Checklist> findByUserId(Long userId);
}
