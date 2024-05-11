# SupportBot v1.0
> Всем привет! Перед вами небольшой бот-помощник для оптово-розничной компании. 

> [!NOTE]
> На данный момент бот находится в разработке и на текущий момент он не работает. Уже в процессе:)

> [!IMPORTANT]
> Создано с помощью:
> - Java 11
> - Telegram Bots
> - RabbitMQ
> - Apache Maven
> - Spring boot 2.7.14
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
> ## Архитектура приложения
> 
> 
> ![Архитектура приложения на схеме](/architecture.jpg)
> 
> 
> Приложение использует паттерн `MVC` (Model, View, Controller)
> 
> `Dispatcher` - является `Controller`, отвечает за получение обновлений(данных) в Telegram через 
> TelegramLongPollingBot. Имеет валидацию данных и сервис авторизации, сообщается с базой для этого. 
> Работает по принципу Controller, всю статичную и первичную нагрузку принимает на себя и пропускает запрос дальше, 
> когда он уже касается бизнес-логики. Далее, определяет тип сообщения, и посредством брокера сообщений 
> отправляет запрос в Node. 
> 
> 
> `Node` - является `Model`, принимает входящие сообщения от RabbitMQ и дальше обрабатывает их в зависимости 
> от типа сообщения. Здесь находится основная бизнес-логика бота. 
>
> 
> В качестве `View` используется API Telegram.
>
> 

## Сборка и запуск для отладки
1. Установленный и сконфигурированный на сервере `PostgreSQL`
2. Установленный и сконфигурированный на сервере `RabbitMQ`
3. Переименовать файл `.env.Default` в `.env`
4. Указать `переменные окружения`
5. Запустить два сервиса