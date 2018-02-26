# Pull from jdk alpine
FROM openjdk:8-jdk-alpine

VOLUME /tmp
# ARG JAR_FILE
ADD target/gtog.jar app.jar
#using java.security.egd for java bug
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]