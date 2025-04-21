# ------------ Stage 1: Build the JAR ------------
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the project
COPY . .

# Package the application
RUN mvn clean package -DskipTests

# ------------ Stage 2: Run the JAR ------------
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the jar from the previous build stage
COPY --from=build /app/target/GrannSnack-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

