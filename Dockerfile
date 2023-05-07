FROM openjdk:17.0.2-jdk-slim-buster
RUN mvn clean package
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cv-platform.jar
ENTRYPOINT ["java","-jar","cv-platform.jar"]