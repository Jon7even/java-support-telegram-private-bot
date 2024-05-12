package com.github.jon7even.controller.in;

import com.github.jon7even.configuration.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Класс для получения сообщений от зарегистрированного Telegram бота для дальнейшей обработки
 *
 * @author Jon7even
 * @version 1.0
 * @apiNote По своей сути является слушателем. Однако, это LongPollingBot, поэтому в реальности, он делает запрос в
 * сам Telegram для получения обновлений. Можно реализовать webhook, тогда приложение будет отвечать быстрей и
 * потреблять меньше ресурсов сервера.
 */
@Slf4j
@Component
public class TelegramConsumerSingle implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    private final BotConfig botConfig;
    private final HandleBotController handleBotController;

    public TelegramConsumerSingle(HandleBotController handleBotController, BotConfig botConfig) {
        this.botConfig = botConfig;
        this.handleBotController = handleBotController;
    }

    /**
     * Переопределение метода получения секретного токена для регистрации Бота в Api Telegram
     */
    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    /**
     * Переопределение метода обработки сообщений, делегирует обработчику входящих сообщений
     */
    @Override
    public void consume(Update update) {
        handleBotController.processUpdate(update);
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    /**
     * Информационное сообщение в лог о текущем состоянии слушателя (boolean состояние)
     */
    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        log.trace("Бот запущен, его текущие состояние: {}", botSession.isRunning());
    }
}
