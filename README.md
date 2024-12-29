# SupportBot v2.0
> Всем привет! Перед вами небольшой внутренний бот-помощник для малой компании. Помогает улучшить бизнес-процессы.
> - Позволяет выдавать сотрудникам презенты для клиентов (временно недоступно)
> - Предоставляет нейросети для сотрудников по API (В процессе рефакторинга)
>


> [!IMPORTANT]
> Создано с помощью:
> - Java 21
> - Telegram Bots Long Polling Spring Boot v. 8.0.0
> - RabbitMQ
> - Apache Maven
> - Spring boot 3.4.1
> - Hibernate
> - PostgreSQL
> - Liquibase
> - Lombok 
> - MapStruct
> - JUnit
> - Mockito
> 


> [!NOTE]
> Что это? Это микросервисное приложение с использованием `API Telegram Bots`. Бот имеет довольно специфическое
> применение (применим именно для конкретной компании), однако перестроить его под любые другие нужды не составит
> труда. Проектируется как шаблон для других ботов. Текущий uptime прошлой версий v1.0 за 12 месяцев - 100%. Имеет два 
> сервиса Dispatcher(Диспетчер, основной) и Node(Узел - бизнес-логика, можно разворачивать несколько копий в кубере). 
>


## Архитектура приложения


![Архитектура приложения на схеме](/docs/images/architecture.jpg)

 
Приложение использует проверенный временем паттерн `MVC` (Model, View, Controller)

`Dispatcher` - является `Controller`, отвечает за получение обновлений(данных) в Telegram через 
TelegramLongPollingBot. Имеет валидацию данных и сервис авторизации, сообщается с базой для этого. 
Работает по принципу Controller, всю статичную и первичную нагрузку принимает на себя и пропускает запрос дальше, 
только когда он уже касается бизнес-логики, экономя ресурсы сервера. Далее, определяет тип сообщения, и посредством 
брокера сообщений отправляет запрос в Node. 

`Node` - является `Model`, принимает входящие сообщения от RabbitMQ и дальше обрабатывает их в зависимости 
от типа сообщения. Здесь находится основная бизнес-логика бота. 

В качестве `View` используется API Telegram.


## Сборка и запуск

### Обязательное наличие на запускаемой машине:

- Java 21
- Docker version 24.0.5 и выше
- Docker Compose version v2.20 и выше

### Действия для запуска:

- Переименовываем файл переменных окружения `.env.Default` в `.env`(требуется внести изменения, создайте токен в телеге)

- Запустить Docker engine (если не работает, найдите инструкцию подключения)

- Находясь в главном каталоге с помощью Maven собрать проект `mvn clean install`
  Проект соберется и запустятся автоматические тесты. Обратите внимание, в тестах используются тест-контейнеры.
  Именно поэтому нужно запустить докер перед выполнением тестов, сначала загрузятся необходимые образы, затем по
  очереди будут запускаться контейнеры. После того как тесты отработают, контейнеры для тестов сами деактивируется,
  но сами образы останутся, их нужно будет удалить. Запускать нужно с профилем загрузки файла .env в IntelliJ IDEA
  есть такая опция, если не вставить, то контекст будет запущен с ошибкой, т.к. нужны необходимые секреты.

- Если есть потребность и не хочется ждать, можно собрать проект без тестов, используя специальный подготовленный
  `maven` профиль для этого, действия почти те же:
  Находясь в главном каталоге с помощью Maven собрать проект `mvn clean install -Pno-tests-tbp`

- Далее, в главном каталоге командой `docker-compose up -d` компилируем образы для контейнеров. Соберется образ СУБД
  PostgreSQL, соберется образ с брокером RabbitMQ и далее наши образы `Dispatcher` и `Node`. Настроены специальные 
  скрипты проверки здоровья контейнеров, которые будут ждать поднятия образов БД и брокера, и только потом стартовать 
  запуск основных сервисов. Если нода запустится первой, не переживайте:), все продумано, тогда у неё выполнятся 
  миграции первой, а диспетчер их уже увидит и не станет дублировать. 


  P.S. Всем интересных проектов!