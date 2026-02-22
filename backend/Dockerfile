## Stage 1: Build
FROM amazoncorretto:21-alpine AS builder

WORKDIR /app

COPY gradle gradle
COPY gradlew .
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle.properties .

COPY common/build.gradle.kts common/
COPY domain/build.gradle.kts domain/
COPY infra/build.gradle.kts infra/
COPY api/build.gradle.kts api/

RUN chmod +x gradlew && ./gradlew dependencies --no-daemon || true

COPY common/src common/src
COPY domain/src domain/src
COPY infra/src infra/src
COPY api/src api/src

RUN ./gradlew :api:bootJar -x test --no-daemon

## Stage 2: Runtime
FROM amazoncorretto:21-alpine AS runtime

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

COPY --from=builder /app/api/build/libs/weddingmate-api.jar app.jar

RUN chown -R appuser:appgroup /app

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "app.jar"]
