package com.weddingmate.domain.user.repository;

import com.weddingmate.domain.user.entity.BusinessProfile;
import com.weddingmate.domain.user.entity.BusinessStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Long> {

    Optional<BusinessProfile> findByUserId(Long userId);

    List<BusinessProfile> findByStatus(BusinessStatus status);

    boolean existsByUserId(Long userId);
}
