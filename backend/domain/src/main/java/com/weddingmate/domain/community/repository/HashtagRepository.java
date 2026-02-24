package com.weddingmate.domain.community.repository;

import com.weddingmate.domain.community.entity.Hashtag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByName(String name);

    List<Hashtag> findByNameContainingIgnoreCaseOrderByUsageCountDesc(String keyword, Pageable pageable);

    List<Hashtag> findAllByOrderByUsageCountDesc(Pageable pageable);
}
