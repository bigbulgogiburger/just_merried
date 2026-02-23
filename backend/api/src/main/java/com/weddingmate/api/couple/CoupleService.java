package com.weddingmate.api.couple;

import com.weddingmate.api.couple.dto.CoupleConnectRequest;
import com.weddingmate.api.couple.dto.CoupleInviteResponse;
import com.weddingmate.common.exception.BusinessException;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.couple.entity.Couple;
import com.weddingmate.domain.couple.entity.CoupleMember;
import com.weddingmate.domain.couple.entity.CoupleRole;
import com.weddingmate.domain.couple.repository.CoupleMemberRepository;
import com.weddingmate.domain.couple.repository.CoupleRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class CoupleService {

    private final CoupleRepository coupleRepository;
    private final CoupleMemberRepository coupleMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public CoupleInviteResponse createInvite(Long userId) {
        if (coupleMemberRepository.existsByUserId(userId)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "이미 커플 연동된 사용자입니다.");
        }

        String inviteCode = generateUniqueCode();
        Couple couple = coupleRepository.save(Couple.builder().inviteCode(inviteCode).build());

        User user = findUser(userId);
        coupleMemberRepository.save(CoupleMember.builder()
                .couple(couple)
                .user(user)
                .roleInCouple(CoupleRole.GROOM)
                .build());

        return new CoupleInviteResponse(inviteCode);
    }

    @Transactional
    public void connect(Long userId, CoupleConnectRequest request) {
        if (coupleMemberRepository.existsByUserId(userId)) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "이미 커플 연동된 사용자입니다.");
        }

        Couple couple = coupleRepository.findByInviteCode(request.inviteCode())
                .orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_INVITE_CODE, "유효하지 않은 초대 코드입니다."));

        User user = findUser(userId);
        coupleMemberRepository.save(CoupleMember.builder()
                .couple(couple)
                .user(user)
                .roleInCouple(request.role())
                .build());

        couple.activate();
    }

    @Transactional
    public void disconnect(Long userId) {
        CoupleMember me = coupleMemberRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.COUPLE_NOT_FOUND, "커플 연동 정보를 찾을 수 없습니다."));

        Long coupleId = me.getCouple().getId();
        coupleMemberRepository.delete(me);

        if (coupleMemberRepository.findByCoupleId(coupleId).isEmpty()) {
            coupleRepository.deleteById(coupleId);
        }
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }

    private String generateUniqueCode() {
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 10; i++) {
            String code = String.format("%06d", random.nextInt(1_000_000));
            if (coupleRepository.findByInviteCode(code).isEmpty()) {
                return code;
            }
        }
        throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "초대코드를 생성하지 못했습니다.");
    }
}
