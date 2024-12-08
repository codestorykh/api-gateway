# Use OpenJDK 23 as the base image
FROM openjdk:23-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the target directory to the container
COPY target/my-application.jar /app/my-application.jar

# Expose the port your application will run on
EXPOSE 8080

# Run the application when the container starts
ENTRYPOINT ["java", "-jar", "my-application.jar"]
