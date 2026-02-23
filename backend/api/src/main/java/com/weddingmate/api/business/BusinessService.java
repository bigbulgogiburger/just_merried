package com.weddingmate.api.business;

import com.weddingmate.api.business.dto.BusinessRegisterRequest;
import com.weddingmate.api.business.dto.BusinessSummaryResponse;
import com.weddingmate.common.exception.BusinessException;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.user.entity.BusinessProfile;
import com.weddingmate.domain.user.entity.BusinessStatus;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.BusinessProfileRepository;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessProfileRepository businessProfileRepository;
    private final UserRepository userRepository;

    @Transactional
    public BusinessSummaryResponse register(Long userId, BusinessRegisterRequest request) {
        if (businessProfileRepository.existsByUserId(userId)) {
            throw new BusinessException(ErrorCode.BUSINESS_ALREADY_REGISTERED, "이미 사업자 등록이 완료된 사용자입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));

        BusinessProfile profile = businessProfileRepository.save(BusinessProfile.builder()
                .user(user)
                .businessName(request.businessName())
                .businessNumber(request.businessNumber())
                .category(request.category())
                .address(request.address())
                .phone(request.phone())
                .description(request.description())
                .build());

        return BusinessSummaryResponse.from(profile);
    }

    @Transactional(readOnly = true)
    public List<BusinessSummaryResponse> pending() {
        return businessProfileRepository.findByStatus(BusinessStatus.PENDING)
                .stream()
                .map(BusinessSummaryResponse::from)
                .toList();
    }

    @Transactional
    public BusinessSummaryResponse approve(Long profileId) {
        BusinessProfile profile = businessProfileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BUSINESS_PROFILE_NOT_FOUND, "사업자 프로필을 찾을 수 없습니다."));

        profile.approve();
        profile.getUser().promoteToVendor();

        return BusinessSummaryResponse.from(profile);
    }
}
