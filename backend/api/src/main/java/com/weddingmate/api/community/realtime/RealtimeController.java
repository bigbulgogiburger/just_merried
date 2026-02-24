package com.weddingmate.api.community.realtime;

import com.weddingmate.infra.auth.security.CurrentUser;
import com.weddingmate.infra.auth.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "Community Realtime", description = "실시간 SSE API")
@RestController
@RequestMapping("/api/v1/community/realtime")
@RequiredArgsConstructor
public class RealtimeController {

    private final SseBroker sseBroker;

    @Operation(summary = "SSE 구독")
    @GetMapping("/subscribe")
    public SseEmitter subscribe(@CurrentUser UserPrincipal principal) {
        return sseBroker.connect(principal.userId());
    }
}
