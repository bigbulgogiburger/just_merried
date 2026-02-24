package com.weddingmate.api.market;

import com.weddingmate.api.market.dto.WishlistCreateRequest;
import com.weddingmate.api.market.dto.WishlistItemResponse;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.market.entity.MarketWishlist;
import com.weddingmate.domain.market.repository.MarketWishlistRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final MarketWishlistRepository marketWishlistRepository;
    private final UserRepository userRepository;

    @Transactional
    public WishlistItemResponse add(Long userId, WishlistCreateRequest request) {
        var existing = marketWishlistRepository.findByUserIdAndTargetTypeAndTargetId(userId, request.targetType(), request.targetId());
        if (existing.isPresent()) return WishlistItemResponse.from(existing.get());
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
        MarketWishlist saved = marketWishlistRepository.save(MarketWishlist.builder().user(user).targetType(request.targetType()).targetId(request.targetId()).build());
        return WishlistItemResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<WishlistItemResponse> myWishlist(Long userId) { return marketWishlistRepository.findByUserIdOrderByCreatedAtDesc(userId).stream().map(WishlistItemResponse::from).toList(); }

    @Transactional
    public void remove(Long userId, WishlistCreateRequest request) { marketWishlistRepository.deleteByUserIdAndTargetTypeAndTargetId(userId, request.targetType(), request.targetId()); }
}
