FROM openjdk:21-jdk-slim
LABEL description="Appsolute Spring Boot Application"
EXPOSE 8080
COPY appsolute-0.0.1-SNAPSHOT.jar /appsolute/app.jar
WORKDIR /appsolute
ENTRYPOINT ["java", "-jar", "app.jar"]