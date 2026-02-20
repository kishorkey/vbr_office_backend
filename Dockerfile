# Step 1: Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Step 2: Add metadata
LABEL maintainer="kishor@example.com"

# Step 3: Add the jar file into the container
COPY target/spring-boot-docker.jar app.jar

# Step 4: Expose port (Spring Boot default is 8080)
EXPOSE 8080

# Step 5: Run the jar file
ENTRYPOINT ["java","-jar","/app.jar","--spring.profiles.active=docker"]
