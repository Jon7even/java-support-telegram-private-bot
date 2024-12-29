FROM amazoncorretto:21-alpine-jdk
COPY target/*.jar node-service.jar
EXPOSE ${SERVER_EXTERNAL_PORT_DISPATCHER}
ENTRYPOINT ["java", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-jar","/node-service.jar"]