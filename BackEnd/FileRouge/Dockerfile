FROM openjdk:17-jdk-alpine
WORKDIR /app
EXPOSE 8080
ARG JAR_FILE=target/fillRouge.jar
ADD ${JAR_FILE} fillRouge
ENTRYPOINT ["java","-jar","fillRouge"]
