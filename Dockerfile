FROM maven:3.9.9-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
COPY --from=build /app/target/myLib-0.0.1-SNAPSHOT.jar myLib.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "myLib.jar"]