FROM maven:3.8.7-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/src/main/resources/docker.application.yaml configs.yaml

USER root
RUN apk add netcat-openbsd
COPY ./ShellScripts/wait-for.sh .
COPY ./ShellScripts/start.sh .

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/app.jar", "--spring.config.location=file:/app/configs.yaml" ]