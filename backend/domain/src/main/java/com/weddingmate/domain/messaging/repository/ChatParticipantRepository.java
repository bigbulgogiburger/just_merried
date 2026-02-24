package com.weddingmate.domain.messaging.repository;

import com.weddingmate.domain.messaging.entity.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
    List<ChatParticipant> findByUserId(Long userId);
    List<ChatParticipant> findByRoomId(Long roomId);
    Optional<ChatParticipant> findByRoomIdAndUserId(Long roomId, Long userId);
    boolean existsByRoomIdAndUserId(Long roomId, Long userId);
}
