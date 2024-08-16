FROM openjdk:21-jdk-slim
WORKDIR /app

EXPOSE 8080

COPY ./target/AOM-demo-0.0.1-SNAPSHOT.jar AOM.jar

ENTRYPOINT ["java", "-jar", "AOM.jar"]
