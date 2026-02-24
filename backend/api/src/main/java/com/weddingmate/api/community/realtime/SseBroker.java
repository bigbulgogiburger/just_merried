package com.weddingmate.api.community.realtime;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseBroker {

    private final Map<Long, Map<String, SseEmitter>> emittersByUser = new ConcurrentHashMap<>();

    public SseEmitter connect(Long userId) {
        SseEmitter emitter = new SseEmitter(30L * 60L * 1000L); // 30min
        String emitterId = UUID.randomUUID().toString();

        emittersByUser.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).put(emitterId, emitter);

        emitter.onCompletion(() -> remove(userId, emitterId));
        emitter.onTimeout(() -> remove(userId, emitterId));
        emitter.onError((e) -> remove(userId, emitterId));

        try {
            emitter.send(SseEmitter.event().name("connected").data("ok"));
        } catch (IOException e) {
            remove(userId, emitterId);
        }

        return emitter;
    }

    public void sendToUser(Long userId, String eventName, Object payload) {
        Map<String, SseEmitter> emitters = emittersByUser.get(userId);
        if (emitters == null || emitters.isEmpty()) return;

        emitters.forEach((id, emitter) -> {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(payload));
            } catch (IOException e) {
                remove(userId, id);
            }
        });
    }

    private void remove(Long userId, String emitterId) {
        Map<String, SseEmitter> emitters = emittersByUser.get(userId);
        if (emitters == null) return;
        emitters.remove(emitterId);
        if (emitters.isEmpty()) {
            emittersByUser.remove(userId);
        }
    }
}
