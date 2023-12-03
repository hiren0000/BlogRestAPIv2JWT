
FROM openjdk:17.0.1-jdk-slim
VOLUME /tmp
COPY target/BlogAPIv2-0.0.1-SNAPSHOT.jar demo.jar
ENTRYPOINT ["java","-jar","/demo.jar"]
EXPOSE 8080
