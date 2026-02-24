package com.weddingmate.domain.messaging.repository;

import com.weddingmate.domain.messaging.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
