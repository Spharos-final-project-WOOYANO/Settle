FROM openjdk:17-alpine
COPY build/libs/*.jar app.jargit
ENTRYPOINT ["java", "-jar", "app.jar"]
