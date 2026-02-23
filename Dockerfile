# Step 1: Use an official OpenJDK 21 base image from Docker Hub
FROM eclipse-temurin:21-jdk-alpine


# Step 2: Set the working directory inside the container
WORKDIR /app


# Step 3: Copy the Spring Boot JAR file into the container
COPY target/livapp-1.0.jar /app/livapp-1.0.jar


# Step 4: Expose the port your application runs on
EXPOSE 8082


# Step 5: Define the command to run your Spring Boot application
CMD ["java", "-jar", "/app/livapp-1.0.jar"]

