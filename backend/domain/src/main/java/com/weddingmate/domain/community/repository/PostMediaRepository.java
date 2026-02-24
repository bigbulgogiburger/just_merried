package com.weddingmate.domain.community.repository;

import com.weddingmate.domain.community.entity.PostMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostMediaRepository extends JpaRepository<PostMedia, Long> {
    List<PostMedia> findByPostIdOrderBySortOrderAsc(Long postId);
}
