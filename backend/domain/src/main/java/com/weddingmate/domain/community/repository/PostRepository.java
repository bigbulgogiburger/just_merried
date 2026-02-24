package com.weddingmate.domain.community.repository;

import com.weddingmate.domain.community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Post> findByRegionOrderByCreatedAtDesc(String region);
}
