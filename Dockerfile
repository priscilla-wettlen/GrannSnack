FROM ubuntu:latest
LABEL authors="jabonfoca"

ENTRYPOINT ["top", "-b"]

# Use an official JDK runtime as a parent image
FROM eclipse-temurin:21-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY target/GrannSnack-0.0.1-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
