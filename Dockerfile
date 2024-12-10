# Use OpenJDK 23 as the base image
FROM openjdk:23-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy your application JAR into the container
COPY target/*.jar /app/api-gateway.jar

# Copy the Datadog Java Agent into the container
COPY dd-java-agent.jar /app/dd-java-agent.jar

# Set environment variables for Datadog
ENV DD_SERVICE=api-gateway
ENV DD_ENV=dev
ENV DD_AGENT_HOST=us5.datadoghq.com
ENV DD_API_KEY='05d95f14fb6e1673ad588552b7938998e2f7'

# Run the application with the Datadog agent
ENTRYPOINT ["java", "-javaagent:/app/dd-java-agent.jar", "-Ddd.service=${DD_SERVICE}", "-Ddd.env=${DD_ENV}", "-Ddd.agent.host=${DD_AGENT_HOST}", "-Ddd.api.key=${DD_API_KEY}", "-jar", "/app/api-gateway.jar"]
