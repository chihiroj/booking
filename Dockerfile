FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

COPY gradlew settings.gradle build.gradle ./
COPY gradle gradle

COPY src src

RUN ./gradlew clean build -x test

FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the generated jar
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
