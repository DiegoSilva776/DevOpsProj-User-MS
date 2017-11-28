FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} target/ms_user-3.5.2.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/target/ms_user-3.5.2.jar"]