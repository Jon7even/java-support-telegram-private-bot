package com.github.jon7even.controller.out;

import com.github.jon7even.configuration.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Класс для отправки сообщений в зарегистрированный Telegram бот
 *
 * @author Jon7even
 * @version 1.0
 */
@Slf4j
@Component
public class SenderBotClient extends OkHttpTelegramClient {
    public SenderBotClient(BotConfig botConfig) {
        super(botConfig.getToken());
        log.trace("Клиент HTTP Telegram загружен");
    }

    /**
     * Метод для отправки текстовых сообщений пользователю
     *
     * @param message заполненное сообщение, обязательно должно иметь chatId
     */
    public void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            } catch (TelegramApiException exception) {
                log.error("Произошла ошибка при отправке ответного сообщения: {}", exception.getMessage());
            }
        }
    }
}
