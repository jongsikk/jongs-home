FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} Influx-API-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/Influx-API-0.0.1-SNAPSHOT.jar"]
