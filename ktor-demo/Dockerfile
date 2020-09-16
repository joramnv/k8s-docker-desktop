FROM openjdk:8-jre-alpine
WORKDIR /app
RUN addgroup -S demo-group && adduser -S demo-user -G demo-group
USER demo-user:demo-group
EXPOSE 8123
ARG JAR_FILE=/build/libs/ktor-demo-*-all.jar
COPY ${JAR_FILE} ./app.jar
ENTRYPOINT ["java","-jar","./app.jar"]
