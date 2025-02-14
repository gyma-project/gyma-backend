FROM eclipse-temurin:21-jdk AS build

WORKDIR /app
COPY target/gyma-0.0.1-SNAPSHOT.jar gyma-0.0.1-SNAPSHOT.jar
EXPOSE 8081
CMD ["java", "-jar", "gyma-0.0.1-SNAPSHOT.jar"]