package com.weddingmate.domain.checklist.repository;

import com.weddingmate.domain.checklist.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {
    List<ChecklistItem> findByChecklistIdOrderBySortOrderAsc(Long checklistId);
}
