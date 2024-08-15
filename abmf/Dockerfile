FROM openjdk:22-jdk
WORKDIR /app

COPY ./target/abmf-1.0-SNAPSHOT.jar ABMF.jar

ENTRYPOINT ["java", "-cp", "ABMF.jar:app/lib/*", "org.example.KafkaToOracle"]
