FROM openjdk:21

WORKDIR /app

COPY target/payment-0.0.1-SNAPSHOT.jar /app/payment-0.0.1-SNAPSHOT.jar

EXPOSE 8070

ENTRYPOINT ["java", "-jar", "payment-0.0.1-SNAPSHOT.jar"]