FROM openjdk:21

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} taskmanagementsystem.jar

ENTRYPOINT ["java", "-jar", "/taskmanagementsystem.jar"]

EXPOSE 8080