package com.weddingmate.api.community.dm;

import com.weddingmate.api.community.dm.dto.DmMessageCreateRequest;
import com.weddingmate.api.community.dm.dto.DmMessageResponse;
import com.weddingmate.api.community.dm.dto.DmRoomCreateRequest;
import com.weddingmate.api.community.dm.dto.DmRoomResponse;
import com.weddingmate.api.community.notification.NotificationEventService;
import com.weddingmate.common.exception.BusinessException;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.messaging.entity.ChatMessage;
import com.weddingmate.domain.messaging.entity.ChatMessageType;
import com.weddingmate.domain.messaging.entity.ChatParticipant;
import com.weddingmate.domain.messaging.entity.ChatRoom;
import com.weddingmate.domain.messaging.entity.ChatRoomType;
import com.weddingmate.domain.messaging.repository.ChatMessageRepository;
import com.weddingmate.domain.messaging.repository.ChatParticipantRepository;
import com.weddingmate.domain.messaging.repository.ChatRoomRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DmService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final NotificationEventService notificationEventService;

    @Transactional
    public DmRoomResponse createOrReuseRoom(Long userId, DmRoomCreateRequest request) {
        if (userId.equals(request.targetUserId())) {
            throw new BusinessException(ErrorCode.FOLLOW_SELF_NOT_ALLOWED, "자기 자신과 DM 방을 만들 수 없습니다.");
        }

        User me = getUser(userId);
        User target = getUser(request.targetUserId());

        ChatRoom room = findDirectRoom(userId, request.targetUserId())
                .orElseGet(() -> {
                    ChatRoom created = chatRoomRepository.save(ChatRoom.builder().type(ChatRoomType.DIRECT).build());
                    chatParticipantRepository.save(ChatParticipant.builder().room(created).user(me).build());
                    chatParticipantRepository.save(ChatParticipant.builder().room(created).user(target).build());
                    return created;
                });

        List<ChatParticipant> participants = chatParticipantRepository.findByRoomId(room.getId());
        ChatParticipant self = participants.stream().filter(p -> p.getUser().getId().equals(userId)).findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.DM_ROOM_NOT_FOUND, "DM 방 참여자를 찾을 수 없습니다."));
        ChatParticipant counterpart = participants.stream().filter(p -> !p.getUser().getId().equals(userId)).findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.DM_ROOM_NOT_FOUND, "상대 참여자를 찾을 수 없습니다."));

        return DmRoomResponse.from(self, counterpart);
    }

    @Transactional(readOnly = true)
    public List<DmRoomResponse> myRooms(Long userId) {
        return chatParticipantRepository.findByUserId(userId).stream()
                .map(self -> {
                    List<ChatParticipant> participants = chatParticipantRepository.findByRoomId(self.getRoom().getId());
                    ChatParticipant counterpart = participants.stream()
                            .filter(p -> !p.getUser().getId().equals(userId))
                            .findFirst()
                            .orElse(self);
                    return DmRoomResponse.from(self, counterpart);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DmMessageResponse> messages(Long userId, Long roomId) {
        verifyParticipant(userId, roomId);
        return chatMessageRepository.findByRoomIdOrderByCreatedAtAsc(roomId).stream()
                .map(DmMessageResponse::from)
                .toList();
    }

    @Transactional
    public DmMessageResponse sendMessage(Long userId, Long roomId, DmMessageCreateRequest request) {
        verifyParticipant(userId, roomId);

        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.DM_ROOM_NOT_FOUND, "DM 방을 찾을 수 없습니다."));
        User sender = getUser(userId);

        ChatMessage message = chatMessageRepository.save(ChatMessage.builder()
                .room(room)
                .sender(sender)
                .content(request.content())
                .messageType(ChatMessageType.TEXT)
                .build());

        chatParticipantRepository.findByRoomId(roomId).stream()
                .map(ChatParticipant::getUser)
                .map(User::getId)
                .filter(id -> !id.equals(userId))
                .forEach(receiverId -> notificationEventService.onDm(userId, receiverId, roomId, message.getId()));

        return DmMessageResponse.from(message);
    }

    private java.util.Optional<ChatRoom> findDirectRoom(Long userId, Long targetUserId) {
        List<ChatParticipant> mine = chatParticipantRepository.findByUserId(userId);
        return mine.stream()
                .map(ChatParticipant::getRoom)
                .filter(room -> room.getType() == ChatRoomType.DIRECT)
                .filter(room -> chatParticipantRepository.existsByRoomIdAndUserId(room.getId(), targetUserId))
                .findFirst();
    }

    private void verifyParticipant(Long userId, Long roomId) {
        if (!chatParticipantRepository.existsByRoomIdAndUserId(roomId, userId)) {
            throw new BusinessException(ErrorCode.DM_ROOM_ACCESS_DENIED, "해당 DM 방에 접근할 수 없습니다.");
        }
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }
}
