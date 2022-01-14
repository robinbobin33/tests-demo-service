FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine

RUN mkdir /app
COPY build/libs/tests-demo-service.jar /app
CMD ["java", "-jar", "/app/tests-demo-service.jar"]

EXPOSE 8080