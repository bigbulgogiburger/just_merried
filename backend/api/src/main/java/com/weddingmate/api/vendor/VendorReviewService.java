package com.weddingmate.api.vendor;

import com.weddingmate.api.vendor.dto.ReviewCreateRequest;
import com.weddingmate.api.vendor.dto.ReviewResponse;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import com.weddingmate.domain.vendor.entity.Review;
import com.weddingmate.domain.vendor.entity.Vendor;
import com.weddingmate.domain.vendor.repository.ReviewRepository;
import com.weddingmate.domain.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorReviewService {

    private final ReviewRepository reviewRepository;
    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewResponse create(Long userId, Long vendorId, ReviewCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.VENDOR_NOT_FOUND, "업체를 찾을 수 없습니다."));

        Review review = reviewRepository.save(Review.builder()
                .user(user)
                .vendor(vendor)
                .rating(request.rating())
                .content(request.content())
                .build());

        refreshVendorRating(vendor);
        return ReviewResponse.from(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> list(Long vendorId) {
        if (!vendorRepository.existsById(vendorId)) {
            throw new NotFoundException(ErrorCode.VENDOR_NOT_FOUND, "업체를 찾을 수 없습니다.");
        }
        return reviewRepository.findByVendorIdOrderByCreatedAtDesc(vendorId)
                .stream()
                .map(ReviewResponse::from)
                .toList();
    }

    private void refreshVendorRating(Vendor vendor) {
        long count = reviewRepository.countByVendorId(vendor.getId());
        long sum = reviewRepository.sumRatingByVendorId(vendor.getId());
        vendor.refreshRating(sum, count);
    }
}
