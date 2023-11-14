FROM openjdk:17-jdk-alpine

COPY . /app
WORKDIR /app

RUN apk add maven
RUN mvn clean install

EXPOSE 8080

CMD [ "java", "-jar", "/app/target/toolschallanger-1.0.0-SNAPSHOT.jar"]

