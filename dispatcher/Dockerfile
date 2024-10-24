FROM amazoncorretto:17.0.11-alpine3.19
COPY target/*.jar dispatcher-service.jar
EXPOSE ${SERVER_EXTERNAL_PORT_DISPATCHER}
ENTRYPOINT ["java", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-jar","/dispatcher-service.jar"]