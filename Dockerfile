FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine
EXPOSE 8081
RUN mkdir -p /app/
ADD build/libs/currency-0.0.1-SNAPSHOT.jar /app/currency-001.jar
ENTRYPOINT ["java", "-jar", "/app/currency-001.jar"]