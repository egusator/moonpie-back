FROM gradle:8.6.0-jdk21 AS builder

WORKDIR /app
COPY build.gradle .
COPY src src

RUN gradle build -x test #убрать -x test после изменения тестов
FROM openjdk:21

COPY --from=builder /app/build/libs/moonpie.jar .

CMD ["java", "-jar", "moonpie.jar"]