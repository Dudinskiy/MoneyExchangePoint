FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/money_exchange_point-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]