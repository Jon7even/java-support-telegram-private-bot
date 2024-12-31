# Загружаем образ Java версии 21
FROM amazoncorretto:21-alpine-jdk

# Выдергиваем скомпилированный исполняемый файл и называем его красиво:)
COPY target/*.jar node-service.jar

# Задаём основной порт, на котором будет работать приложение
EXPOSE ${SERVER_EXTERNAL_PORT_NODE}

# С какими условиями запускать приложение: ограничения по памяти кучи, текущий профиль, название исполняемого файла
ENTRYPOINT ["java", "-Xms128m", "-Xmx256m", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-jar","/node-service.jar"]