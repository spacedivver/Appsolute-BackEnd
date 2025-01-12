#빌드 단계
FROM openjdk:21-jre-slim as build

WORKDIR /app
COPY ./build/libs/*-0.0.1-SNAPSHOT.jar /app/app.jar

# 실행 단계
FROM openjdk:21-jre-slim

EXPOSE 8080
COPY --from=build /app/app.jar /appsolute/app.jar
WORKDIR /appsolute
ENTRYPOINT ["java", "-jar", "app.jar"]