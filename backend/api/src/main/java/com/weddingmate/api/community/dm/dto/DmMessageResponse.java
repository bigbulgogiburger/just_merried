package com.weddingmate.api.community.dm.dto;

import com.weddingmate.domain.messaging.entity.ChatMessage;

import java.time.LocalDateTime;

public record DmMessageResponse(
        Long id,
        Long roomId,
        Long senderId,
        String content,
        String messageType,
        LocalDateTime createdAt
) {
    public static DmMessageResponse from(ChatMessage message) {
        return new DmMessageResponse(
                message.getId(),
                message.getRoom().getId(),
                message.getSender().getId(),
                message.getContent(),
                message.getMessageType().name(),
                message.getCreatedAt()
        );
    }
}
