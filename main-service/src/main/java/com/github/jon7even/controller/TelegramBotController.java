package com.github.jon7even.controller;

import com.github.jon7even.config.BotConfig;
import com.github.jon7even.model.UserEntity;
import com.github.jon7even.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public final class TelegramBotController extends TelegramLongPollingBot {
    private final BotConfig config;
    private final UserRepository userRepository;

    public TelegramBotController(BotConfig config, UserRepository userRepository) {
        super(config.getToken());
        this.config = config;
        this.userRepository = userRepository;
        List<BotCommand> commandsMenu = new ArrayList<>();
        commandsMenu.add(new BotCommand("/start", "Описание и команды бота"));
        commandsMenu.add(new BotCommand("/help", "Список доступных команд"));
        commandsMenu.add(new BotCommand("/items", "Дать информацию о товаре"));
        commandsMenu.add(new BotCommand("/checkitems", "Новая работа с товарами"));
        commandsMenu.add(new BotCommand("/newtask", "Добавить задачу для команды"));
        commandsMenu.add(new BotCommand("/tasklist", "Получить задачи к выполнению"));
        commandsMenu.add(new BotCommand("/gifts", "Начать работать с подарками"));
        commandsMenu.add(new BotCommand("/checkgifts", "Проверить кто еще без подарка"));
        commandsMenu.add(new BotCommand("/competitors", "Работа с конкурентами"));
        commandsMenu.add(new BotCommand("/userinfo", "Информация о себе"));
        log.debug("Меню бота инициализировано");

        try {
            this.execute(new SetMyCommands(commandsMenu, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Что-то пошло не так с загрузкой команд для бота: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String result = update.getMessage().getText();
            long chaId = update.getMessage().getChatId();

            switch (result) {
                case "/start":
                    registerUser(update.getMessage());
                    startCommandReceived(chaId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    sendMessage(chaId, "текст");
                    break;
                case "/items":
                    sendMessage(chaId, "текст");
                    break;
                case "/checkitems":
                    sendMessage(chaId, "текст");
                    break;
                case "/newtask":
                    sendMessage(chaId, "текст");
                    break;
                case "/tasklist":
                    sendMessage(chaId, "текст");
                    break;
                case "/gifts":
                    sendMessage(chaId, "текст");
                    break;
                case "/checkgifts":
                    sendMessage(chaId, "текст");
                    break;
                case "/competitors":
                    sendMessage(chaId, "текст");
                    break;
                case "/userinfo":
                    sendMessage(chaId, "текст");
                    break;

                default:
                    sendMessage(chaId, "текст");
                    log.error("Эту команду мы еще не поддерживаем. Команда пользователя: " + result);
            }
        }

    }

    private void startCommandReceived(long chatId, String name) {
        String answer = "Добрый день, " + name + ", наш бот имеет следующий функционал";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void registerUser(Message message) {
        var chatId = message.getChatId();

        if (userRepository.existsByChatId(chatId)) {
            log.info("Это наш старожил, пользователь уже есть в системе.");
            log.debug("Это наш старожил, пользователь уже есть в системе с tgId={}", chatId);
        } else {
            log.info("Начинаю регистрацию нового пользователя");
            log.debug("Начинаю регистрацию нового пользователя с tgId={}", chatId);

            var chat = message.getChat();
            UserEntity user = UserEntity.builder()
                    .chatId(chatId)
                    .firstName(chat.getFirstName())
                    .lastName(chat.getLastName())
                    .userName(chat.getUserName())
                    .registeredOn(LocalDateTime.now())
                    .build();
            log.debug("Новый пользователь собран user={}", user);

            userRepository.save(user);
            log.debug("Пользователь успешно сохранен в БД");
        }
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

}
