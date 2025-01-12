package com.github.jon7even.service.in.status;

import com.github.jon7even.telegram.BotState;

/**
 * Интерфейс сервиса отвечающего за работу со статусами пользователей
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Данный сервис является неотъемлемой частью приложения. Он позволяет вычислить текущий статус
 * бота для конкретного пользователя и в соответствии с ним обработать необходимую логику. Конкретно для данного
 * проекта, было решено использовать реляционную БД postgreSQL. Но логичней все-таки использовать Redis работая
 * с telegramId и по ключу доставать необходимые статусы. При подключении Redis следует помнить и учесть
 * следующий момент: сервис Node может запускаться в неограниченном количестве копий.
 */
public interface UserStatusService {

    /**
     * Метод для установки статуса бота для конкретного пользователя.
     *
     * @param chatId   ID пользователя из Telegram
     * @param botState перечисление {@link BotState} cо статусом, который нужно установить
     */
    void setBotStateForUser(Long chatId, BotState botState);

    /**
     * Метод для установки статуса бота для конкретного пользователя.
     *
     * @param chatId ID пользователя из Telegram
     * @return перечисление {@link BotState} с текущим статусом для пользователя
     */
    BotState getBotStateForUser(Long chatId);
}