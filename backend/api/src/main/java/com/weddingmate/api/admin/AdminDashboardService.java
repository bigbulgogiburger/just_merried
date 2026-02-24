package com.weddingmate.api.admin;

import com.weddingmate.domain.community.repository.PostRepository;
import com.weddingmate.domain.market.repository.SecondhandProductRepository;
import com.weddingmate.domain.user.entity.BusinessStatus;
import com.weddingmate.domain.user.repository.BusinessProfileRepository;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service @RequiredArgsConstructor
public class AdminDashboardService {
    private final UserRepository userRepository; private final PostRepository postRepository; private final SecondhandProductRepository secondhandProductRepository; private final BusinessProfileRepository businessProfileRepository;

    @Transactional(readOnly = true)
    public Map<String, Object> summary(){
        return Map.of(
                "userCount", userRepository.count(),
                "postCount", postRepository.count(),
                "secondhandCount", secondhandProductRepository.count(),
                "pendingBusinessCount", businessProfileRepository.findByStatus(BusinessStatus.PENDING).size(),
                "reportQueueCount", 0,
                "moderationQueueCount", 0
        );
    }
}
