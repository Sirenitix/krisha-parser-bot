FROM registry.almanit.kz/devops/resources/openjdk:17-alpine

COPY ./target/*.jar  /opt/app.jar
WORKDIR /opt
ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 9030
