FROM gradle:8-jdk21 AS builder
WORKDIR /build
COPY . /build/
RUN gradle build -x test

FROM openjdk:21-slim
WORKDIR /app
COPY --from=builder /build/build/libs/pnc-parcial-final-hotel-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

