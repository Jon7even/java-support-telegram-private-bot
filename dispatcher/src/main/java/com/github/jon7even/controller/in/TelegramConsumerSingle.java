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

@Slf4j
@Component
public class TelegramConsumerSingle implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    private final BotConfig botConfig;
    private final ConsumerBotController consumerBotController;

    public TelegramConsumerSingle(ConsumerBotController consumerBotController, BotConfig botConfig) {
        this.botConfig = botConfig;
        this.consumerBotController = consumerBotController;
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void consume(Update update) {
        consumerBotController.processUpdate(update);
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        log.trace("Бот запущен, его текущие состояние: {}", botSession.isRunning());
    }
}
