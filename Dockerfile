# Use the official Gradle image as a build stage
FROM gradle:8.9-jdk22 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle settings.gradle ./
COPY gradle gradle

# Download dependencies (this will cache dependencies)
RUN gradle build --no-daemon

# Copy the rest of the application code
COPY . .

# Build the application
RUN gradle bootJar --no-daemon

# Use a lightweight JRE image for the final stage
FROM eclipse-temurin:22-jre

# Set the working directory for the final image
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]