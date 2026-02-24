package com.weddingmate.api.integration;

import com.weddingmate.api.auth.AuthController;
import com.weddingmate.api.auth.dto.LoginRequest;
import com.weddingmate.api.auth.dto.RefreshRequest;
import com.weddingmate.api.auth.dto.TokenResponse;
import com.weddingmate.api.community.PostController;
import com.weddingmate.api.community.PostService;
import com.weddingmate.api.community.dto.PostDetailResponse;
import com.weddingmate.api.couple.CoupleController;
import com.weddingmate.api.couple.CoupleService;
import com.weddingmate.api.couple.dto.CoupleConnectRequest;
import com.weddingmate.api.couple.dto.CoupleInviteResponse;
import com.weddingmate.domain.couple.entity.CoupleRole;
import com.weddingmate.domain.user.entity.OAuthProvider;
import com.weddingmate.infra.auth.jwt.JwtTokenProvider;
import com.weddingmate.infra.auth.oauth.AuthService;
import com.weddingmate.infra.auth.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class Sprint10FlowIntegrationTest {

    private AuthService authService;
    private CoupleService coupleService;
    private PostService postService;

    private AuthController authController;
    private CoupleController coupleController;
    private PostController postController;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        coupleService = mock(CoupleService.class);
        postService = mock(PostService.class);

        authController = new AuthController(authService);
        coupleController = new CoupleController(coupleService);
        postController = new PostController(postService);
    }

    @Test
    @DisplayName("회원가입/로그인~토큰 재발급 플로우를 검증한다")
    void authFlow() {
        JwtTokenProvider.TokenPair loginPair = new JwtTokenProvider.TokenPair("access-1", "refresh-1");
        JwtTokenProvider.TokenPair refreshPair = new JwtTokenProvider.TokenPair("access-2", "refresh-2");
        given(authService.login(eq(OAuthProvider.KAKAO), any(Map.class))).willReturn(loginPair);
        given(authService.refresh("refresh-1")).willReturn(refreshPair);

        var loginResponse = authController.login("kakao", new LoginRequest("oauth-access-token"));
        var refreshResponse = authController.refresh(new RefreshRequest("refresh-1"));

        assertThat(loginResponse.getBody()).isNotNull();
        TokenResponse loginToken = loginResponse.getBody().data();
        assertThat(loginToken.accessToken()).isEqualTo("access-1");
        assertThat(loginToken.refreshToken()).isEqualTo("refresh-1");

        assertThat(refreshResponse.getBody()).isNotNull();
        TokenResponse refreshedToken = refreshResponse.getBody().data();
        assertThat(refreshedToken.accessToken()).isEqualTo("access-2");
        assertThat(refreshedToken.refreshToken()).isEqualTo("refresh-2");
    }

    @Test
    @DisplayName("온보딩 이후 커플 연동 플로우를 검증한다")
    void coupleFlow() {
        UserPrincipal principal = new UserPrincipal(101L, "user@wm.io", "ROLE_USER");
        given(coupleService.createInvite(101L)).willReturn(new CoupleInviteResponse("CP-INVITE-001"));

        var inviteResponse = coupleController.invite(principal);
        coupleController.connect(principal, new CoupleConnectRequest("CP-INVITE-001", CoupleRole.GROOM));

        assertThat(inviteResponse.getBody()).isNotNull();
        assertThat(inviteResponse.getBody().data().inviteCode()).isEqualTo("CP-INVITE-001");
        verify(coupleService).connect(eq(101L), any(CoupleConnectRequest.class));
    }

    @Test
    @DisplayName("커뮤니티 게시글 조회 응답 스키마를 검증한다")
    void communityFlow() {
        PostDetailResponse detailResponse = new PostDetailResponse(
                301L,
                101L,
                "스드메 견적 공유합니다",
                "서울",
                12,
                3,
                LocalDateTime.now(),
                List.of(),
                List.of("스드메", "결혼준비")
        );
        given(postService.detail(301L)).willReturn(detailResponse);

        var response = postController.detail(301L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().data().id()).isEqualTo(301L);
        assertThat(response.getBody().data().hashtags()).contains("스드메");
    }
}
