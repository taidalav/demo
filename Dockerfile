FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
ENV JAVA_USER_OPTS="${JAVA_USER_OPTS} -Dfile.encoding=UTF-8"
EXPOSE 8080
