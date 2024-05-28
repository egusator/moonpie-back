FROM gradle:8.4.0-jdk21 AS builder
WORKDIR /app
COPY . /app
RUN ./gradlew  bootJar

FROM gradle:8.4.0-jdk21
WORKDIR /app
COPY --from=builder /app/build/libs/ .
CMD java -jar moonpie.jar