package com.weddingmate.api.vendor;

import com.weddingmate.api.vendor.dto.VendorCreateRequest;
import com.weddingmate.api.vendor.dto.VendorDetailResponse;
import com.weddingmate.api.vendor.dto.VendorListResponse;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import com.weddingmate.domain.vendor.entity.Vendor;
import com.weddingmate.domain.vendor.entity.VendorCategory;
import com.weddingmate.domain.vendor.entity.VendorStatus;
import com.weddingmate.domain.vendor.repository.VendorCategoryRepository;
import com.weddingmate.domain.vendor.repository.VendorImageRepository;
import com.weddingmate.domain.vendor.repository.VendorPriceRepository;
import com.weddingmate.domain.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;
    private final VendorCategoryRepository vendorCategoryRepository;
    private final VendorImageRepository vendorImageRepository;
    private final VendorPriceRepository vendorPriceRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<VendorListResponse> list(Long categoryId, String region, Long minPrice, Long maxPrice, String sort) {
        long min = minPrice != null ? minPrice : 0L;
        long max = maxPrice != null ? maxPrice : Long.MAX_VALUE;
        String regionKeyword = region != null ? region.toLowerCase() : "";

        Comparator<Vendor> comparator = switch (sort == null ? "latest" : sort) {
            case "rating" -> Comparator.comparing(Vendor::getRatingAvg).reversed()
                    .thenComparing(Vendor::getRatingCount, Comparator.reverseOrder());
            case "price_asc" -> Comparator.comparingLong(Vendor::getMinPrice);
            case "price_desc" -> Comparator.comparingLong(Vendor::getMaxPrice).reversed();
            default -> Comparator.comparing(Vendor::getCreatedAt).reversed();
        };

        return vendorRepository.findByStatus(VendorStatus.ACTIVE).stream()
                .filter(v -> categoryId == null || v.getCategory().getId().equals(categoryId))
                .filter(v -> regionKeyword.isBlank() || v.getRegion().toLowerCase().contains(regionKeyword))
                .filter(v -> v.getMinPrice() >= min)
                .filter(v -> v.getMaxPrice() <= max)
                .sorted(comparator)
                .map(VendorListResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public VendorDetailResponse detail(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.VENDOR_NOT_FOUND, "업체를 찾을 수 없습니다."));

        List<VendorDetailResponse.ImageResponse> images = vendorImageRepository.findByVendorIdOrderBySortOrderAsc(vendorId)
                .stream()
                .map(VendorDetailResponse.ImageResponse::from)
                .toList();

        List<VendorDetailResponse.PriceResponse> prices = vendorPriceRepository.findByVendorIdOrderBySortOrderAsc(vendorId)
                .stream()
                .map(VendorDetailResponse.PriceResponse::from)
                .toList();

        return VendorDetailResponse.of(vendor, images, prices);
    }

    @Transactional
    public VendorDetailResponse create(VendorCreateRequest request) {
        VendorCategory category = vendorCategoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.VENDOR_CATEGORY_NOT_FOUND, "업체 카테고리를 찾을 수 없습니다."));

        User owner = null;
        if (request.ownerUserId() != null) {
            owner = userRepository.findById(request.ownerUserId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
        }

        Vendor vendor = vendorRepository.save(Vendor.builder()
                .ownerUser(owner)
                .category(category)
                .name(request.name())
                .description(request.description())
                .region(request.region())
                .address(request.address())
                .phone(request.phone())
                .homepageUrl(request.homepageUrl())
                .minPrice(request.minPrice() != null ? request.minPrice() : 0L)
                .maxPrice(request.maxPrice() != null ? request.maxPrice() : 0L)
                .build());

        return VendorDetailResponse.of(vendor, List.of(), List.of());
    }
}
