# Gradle
FROM gradle:8.9.0-jdk21-jammy AS build

WORKDIR /home/gradle/src

COPY --chown=gradle:gradle . /home/gradle/src

RUN gradle build --no-daemon

# OpenJDK
FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/Segsat-Technical-Challenge-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8080

CMD ["java", "-jar", "./app.jar"]
