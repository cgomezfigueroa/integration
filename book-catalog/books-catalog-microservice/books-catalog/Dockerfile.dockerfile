FROM openjdk:23-jdk-slim

WORKDIR /app

COPY target/books-catalog-0.0.1-SNAPSHOT.jar /app/books-catalog-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "books-catalog-0.0.1-SNAPSHOT.jar"]