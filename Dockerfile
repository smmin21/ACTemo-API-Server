FROM openjdk:17
ARG JAR_FILE=build/libs/server-0.0.1-SNAPSHOT.jar
VOLUME /tmp
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
