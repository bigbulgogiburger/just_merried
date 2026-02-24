package com.weddingmate.domain.life.repository;

import com.weddingmate.domain.life.entity.NewHomeChecklistItem;
import com.weddingmate.domain.life.entity.NewHomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewHomeChecklistItemRepository extends JpaRepository<NewHomeChecklistItem, Long> {
    List<NewHomeChecklistItem> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<NewHomeChecklistItem> findByUserIdAndCategoryOrderByCreatedAtDesc(Long userId, NewHomeCategory category);
}
