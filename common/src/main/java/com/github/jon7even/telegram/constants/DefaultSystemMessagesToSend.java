package com.github.jon7even.telegram.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс для типовых служебных сообщений, которые отправляются пользователю
 *
 * @author Jon7even
 * @version 2.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultSystemMessagesToSend {

    public static final String ERROR_SEND_TEXT = "что-то произошло с сервисом ответов на сообщения, "
            + "свяжитесь пожалуйста с администратором";

    public static final String WE_NOT_SUPPORT = "мы еще не поддерживаем";

    public static final String ERROR_TO_SEND = "Извините, произошла ошибка: ";

    public static final String ERROR_RECEIVE = "что-то произошло с сервисом обработки сообщений, "
            + "свяжитесь пожалуйста с администратором";
}