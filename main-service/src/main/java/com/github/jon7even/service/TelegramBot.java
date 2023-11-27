package com.github.jon7even.service;

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
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig config;

    public TelegramBot(BotConfig config) {
        super(config.getBotToken());
        this.config = config;
        List<BotCommand> commandsMenu = new ArrayList<>();
        commandsMenu.add(new BotCommand("/1", "текст"));
        commandsMenu.add(new BotCommand("/2", "текст"));
        commandsMenu.add(new BotCommand("/3", "текст"));

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
        return config.getBotName();
    }
}
