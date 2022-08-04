FROM openjdk:11
ADD target/authorization-server-0.0.1-SNAPSHOT.jar authorization.jar
EXPOSE 9191
ENTRYPOINT ["java", "-jar", "authorization.jar"]