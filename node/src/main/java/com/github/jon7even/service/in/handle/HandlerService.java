package com.github.jon7even.service.in.handle;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс сервиса обработки данных от пользователей.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Обрабатывает данные приходящие от пользователей из API Telegram.
 * <ul>
 *     <li>{@link #processTextMessage(Update)}: процесс обработки текстового сообщения</li>
 *     <li>{@link #processCallbackQuery(Update)}: процесс обработки нажатия на клавиатуру</li>
 * </ul>
 */
public interface HandlerService {

    /**
     * Метод для обработки текстовых сообщений.
     *
     * @param update заполненный {@link Update} из Telegram API
     */
    void processTextMessage(Update update);

    /**
     * Метод для обработки нажатия на клавиатуру.
     *
     * @param update заполненный {@link Update} из Telegram API
     */
    void processCallbackQuery(Update update);
}