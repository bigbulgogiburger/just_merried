package com.weddingmate.domain.user.repository;

import com.weddingmate.domain.user.entity.OAuthProvider;
import com.weddingmate.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByProviderAndProviderId(OAuthProvider provider, String providerId);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    List<User> findByNicknameContainingIgnoreCase(String keyword, Pageable pageable);
}
