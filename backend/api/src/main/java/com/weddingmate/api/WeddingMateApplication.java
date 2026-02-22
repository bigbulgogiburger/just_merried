package com.weddingmate.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.weddingmate")
@EntityScan(basePackages = "com.weddingmate.domain")
@EnableJpaRepositories(basePackages = "com.weddingmate.domain")
@EnableJpaAuditing
public class WeddingMateApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeddingMateApplication.class, args);
    }
}
