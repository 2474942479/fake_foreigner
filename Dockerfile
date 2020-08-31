FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/online_edu.jar online_edu.jar
ENTRYPOINT ["java","-jar","/online_edu.jar", "&"]
