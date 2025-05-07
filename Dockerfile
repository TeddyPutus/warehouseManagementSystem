FROM maven:latest as builder
LABEL authors="Teddy Putus"

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B clean package --file pom.xml -DskipTests

FROM openjdk:21
COPY --from=builder /app/target/warehouseManagementSystem-1.0-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
