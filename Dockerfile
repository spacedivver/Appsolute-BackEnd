FROM openjdk:21-jdk-slim
LABEL description="Appsolute Spring Boot Application"

# 포트 노출
EXPOSE 8080

# /appsolute 디렉토리 생성
RUN mkdir -p /appsolute

# JAR 파일 복사
COPY ./build/libs/*-0.0.1-SNAPSHOT.jar /appsolute/app.jar

# 작업 디렉토리 설정
WORKDIR /appsolute

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]