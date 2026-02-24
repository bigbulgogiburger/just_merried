package com.weddingmate.api.community.dm.dto;

import com.weddingmate.domain.messaging.entity.ChatParticipant;

import java.time.LocalDateTime;

public record DmRoomResponse(
        Long roomId,
        Long counterpartUserId,
        String counterpartNickname,
        String counterpartProfileImageUrl,
        LocalDateTime joinedAt
) {
    public static DmRoomResponse from(ChatParticipant self, ChatParticipant counterpart) {
        return new DmRoomResponse(
                self.getRoom().getId(),
                counterpart.getUser().getId(),
                counterpart.getUser().getNickname(),
                counterpart.getUser().getProfileImageUrl(),
                self.getJoinedAt()
        );
    }
}
