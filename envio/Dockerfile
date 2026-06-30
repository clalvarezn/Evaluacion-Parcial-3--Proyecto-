FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/envio-0.0.1-SNAPSHOT.jar app.jar
RUN ./mvnw clean package -DskipTests
EXPOSE 8085
ENTRYPOINT ["java","-jar","/app/app.jar"]