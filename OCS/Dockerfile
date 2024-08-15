FROM openjdk:22-jdk
WORKDIR /app

COPY ./target/OCS-1.0-SNAPSHOT.jar OCS.jar

ENTRYPOINT ["java", "-jar", "OCS.jar"]