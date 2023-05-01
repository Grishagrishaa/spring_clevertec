FROM openjdk:17-alpine
MAINTAINER Grisha
COPY build/libs/spring_task_1-1.0.jar spring_task_1-1.0.jar
ENTRYPOINT ["java","-jar","/spring_task_1-1.0.jar"]