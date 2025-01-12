# 빌드 단계
FROM openjdk:21-jdk-slim as build

# JAR 파일 빌드
WORKDIR /app
COPY ./build/libs/*-0.0.1-SNAPSHOT.jar /app/app.jar

# 실행 단계
FROM openjdk:21-jdk-slim

# 애플리케이션 실행
EXPOSE 8080
COPY --from=build /app/app.jar /appsolute/app.jar
WORKDIR /appsolute
ENTRYPOINT ["java", "-jar", "app.jar"]