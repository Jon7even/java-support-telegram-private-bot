package com.github.jon7even.controller;

import com.github.jon7even.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public final class TelegramBotController extends TelegramLongPollingBot {
    private final BotConfig config;

    public TelegramBotController(BotConfig config) {
        super(config.getToken());
        this.config = config;
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
                case "/1":
                    startCommandReceived(chaId, update.getMessage().getChat().getFirstName());
                    break;
                case "/2":
                    sendMessage(chaId, "текст");
                    break;
                case "/3":
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

    @Override
    public String getBotUsername() {
        return config.getName();
    }

}
