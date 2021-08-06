FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/fake_foreigner.jar fake_foreigner.jar
ENTRYPOINT ["java","-jar","/fake_foreigner.jar", "&"]
