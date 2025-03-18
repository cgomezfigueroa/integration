FROM eclipse-temurin:23-jdk
WORKDIR /app
EXPOSE 8080
COPY target/*.jar /app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]
