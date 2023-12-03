
FROM openjdk:17.0.1-jdk-slim
VOLUME /tmp
COPY target/BlogAPIv2-0.0.1-SNAPSHOT.jar blogAPIv2.jar
ENTRYPOINT ["java","-jar","/blogAPIv2.jar"]
EXPOSE 8080
