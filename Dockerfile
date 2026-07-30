FROM gradle:8-jdk17 AS build

WORKDIR /app

COPY . .

RUN ./gradlew bootJar


FROM eclipse-temurin:17

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]