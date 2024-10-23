# SupportBot v1.0
> Всем привет! Перед вами небольшой внутренний бот-помощник для малой компании. Помогает улучшить бизнес-процессы. 
> - Работает с товарами по артикулам (В процессе рефакторинга)
> - Работает с клиентами для выдачи презентов (В процессе рефакторинга)
> - Предоставляет нейросети для сотрудников по API (В процессе рефакторинга)


> [!IMPORTANT]
> Создано с помощью:
> - Java 17
> - Telegram Bots
> - RabbitMQ
> - Apache Maven
> - Spring boot 3.2.5
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
> труда. Проектируется как шаблон для других ботов. Текущий uptime прошлых версий за 4 месяца - 100%. Имеет два 
> сервиса Dispatcher(Диспетчер) и Node(Узел). RabbitMQ и PostgreSQL разворачиваются отдельно на сервере. 
> При желании проект можно оперативно перевести на Docker.
>


## Архитектура приложения


![Архитектура приложения на схеме](/docs/images/architecture.jpg)

 
Приложение использует паттерн `MVC` (Model, View, Controller)

`Dispatcher` - является `Controller`, отвечает за получение обновлений(данных) в Telegram через 
TelegramLongPollingBot. Имеет валидацию данных и сервис авторизации, сообщается с базой для этого. 
Работает по принципу Controller, всю статичную и первичную нагрузку принимает на себя и пропускает запрос дальше, 
только когда он уже касается бизнес-логики. Далее, определяет тип сообщения, и посредством брокера сообщений 
отправляет запрос в Node. 

`Node` - является `Model`, принимает входящие сообщения от RabbitMQ и дальше обрабатывает их в зависимости 
от типа сообщения. Здесь находится основная бизнес-логика бота. 

В качестве `View` используется API Telegram.


## Сборка и запуск для отладки
1. Установленный и сконфигурированный на сервере `PostgreSQL`
2. Установленный и сконфигурированный на сервере `RabbitMQ`
3. Переименовать файл `.env.Default` в `.env`
4. Указать `переменные окружения`
5. Запустить два сервиса