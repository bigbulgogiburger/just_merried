package com.weddingmate.api.vendor;

import com.weddingmate.api.vendor.dto.VendorCompareResponse;
import com.weddingmate.common.exception.BusinessException;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import com.weddingmate.domain.vendor.entity.CompareItem;
import com.weddingmate.domain.vendor.entity.Favorite;
import com.weddingmate.domain.vendor.entity.Vendor;
import com.weddingmate.domain.vendor.repository.CompareItemRepository;
import com.weddingmate.domain.vendor.repository.FavoriteRepository;
import com.weddingmate.domain.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorInteractionService {

    private static final long COMPARE_MAX_COUNT = 5;

    private final FavoriteRepository favoriteRepository;
    private final CompareItemRepository compareItemRepository;
    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addFavorite(Long userId, Long vendorId) {
        if (favoriteRepository.findByUserIdAndVendorId(userId, vendorId).isPresent()) return;
        favoriteRepository.save(Favorite.builder().user(getUser(userId)).vendor(getVendor(vendorId)).build());
    }

    @Transactional
    public void removeFavorite(Long userId, Long vendorId) {
        favoriteRepository.findByUserIdAndVendorId(userId, vendorId).ifPresent(favoriteRepository::delete);
    }

    @Transactional
    public void addCompare(Long userId, Long vendorId) {
        if (compareItemRepository.findByUserIdAndVendorId(userId, vendorId).isPresent()) return;

        long current = compareItemRepository.countByUserId(userId);
        if (current >= COMPARE_MAX_COUNT) {
            throw new BusinessException(
                    ErrorCode.VENDOR_COMPARE_LIMIT_EXCEEDED,
                    "비교함은 최대 %d개까지 담을 수 있습니다.".formatted(COMPARE_MAX_COUNT)
            );
        }

        compareItemRepository.save(CompareItem.builder().user(getUser(userId)).vendor(getVendor(vendorId)).build());
    }

    @Transactional
    public void removeCompare(Long userId, Long vendorId) {
        compareItemRepository.findByUserIdAndVendorId(userId, vendorId).ifPresent(compareItemRepository::delete);
    }

    @Transactional(readOnly = true)
    public List<VendorCompareResponse> listCompare(Long userId) {
        return compareItemRepository.findByUserId(userId).stream()
                .map(VendorCompareResponse::from)
                .toList();
    }

    private Vendor getVendor(Long vendorId) {
        return vendorRepository.findById(vendorId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.VENDOR_NOT_FOUND, "업체를 찾을 수 없습니다."));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }
}
