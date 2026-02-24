package com.weddingmate.api.commerce;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j @Component @RequiredArgsConstructor
public class EscrowAutoConfirmScheduler {
    private final EscrowService escrowService;
    @Scheduled(cron = "0 0 * * * *")
    public void run(){ int count = escrowService.autoConfirmDueEscrow(); if(count>0) log.info("auto confirmed escrow count={}", count); }
}
