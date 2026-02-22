package com.weddingmate.infra.auth.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private long accessTokenExpiration = 1800000; // 30 minutes
    private long refreshTokenExpiration = 1209600000; // 14 days
    private String publicKeyPath;
    private String privateKeyPath;
}
