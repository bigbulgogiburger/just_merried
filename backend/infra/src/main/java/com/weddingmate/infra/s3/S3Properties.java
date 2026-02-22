package com.weddingmate.infra.s3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cloud.aws")
public class S3Properties {

    private S3 s3 = new S3();
    private CloudFront cloudfront = new CloudFront();

    @Getter
    @Setter
    public static class S3 {
        private String bucket;
        private String region = "ap-northeast-2";
    }

    @Getter
    @Setter
    public static class CloudFront {
        private String domain;
        private String keyPairId;
        private String privateKeyPath;
    }
}
