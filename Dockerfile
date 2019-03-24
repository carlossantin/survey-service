FROM openjdk:8u171-alpine3.7
COPY build/libs/*.jar app.jar
CMD java ${JAVA_OPTS} -jar app.jar