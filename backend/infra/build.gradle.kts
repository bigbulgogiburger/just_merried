plugins {
    `java-library`
}

dependencies {
    api(project(":domain"))

    // Redis
    api("org.springframework.boot:spring-boot-starter-data-redis")
    api("org.springframework.session:spring-session-data-redis")
    api("org.apache.commons:commons-pool2")

    // Actuator (for health indicators)
    api("org.springframework.boot:spring-boot-starter-actuator")

    // AWS SDK v2
    api("software.amazon.awssdk:s3")
    api("software.amazon.awssdk:cloudfront")
    api("software.amazon.awssdk:sts")

    // OAuth2
    api("org.springframework.boot:spring-boot-starter-oauth2-client")

    // JWT
    api("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("com.redis:testcontainers-redis:2.2.2")
}
