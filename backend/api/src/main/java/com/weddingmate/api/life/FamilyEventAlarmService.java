package com.weddingmate.api.life;

import com.weddingmate.domain.life.repository.FamilyEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j @Component @RequiredArgsConstructor
public class FamilyEventAlarmService {
    private final FamilyEventRepository familyEventRepository;

    @Scheduled(cron = "0 0 9 * * *")
    public void publishUpcomingEventAlarm(){
        LocalDate today = LocalDate.now();
        int count = familyEventRepository.findAll().stream().filter(e -> {
            long d = e.getEventDate().toEpochDay() - today.toEpochDay();
            return d == 7 || d == 3 || d == 1 || d == 0;
        }).toList().size();
        log.info("[FamilyEventAlarm] due events={}", count);
    }
}
