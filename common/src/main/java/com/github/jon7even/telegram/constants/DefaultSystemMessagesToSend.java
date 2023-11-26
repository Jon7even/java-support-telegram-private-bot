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
    public static final String WE_NOT_SUPPORT = "мы еще не поддерживаем";
    public static final String ERROR_TO_EXECUTION_FOR_USER = "Произошла ошибка в процессе ответа пользователю Error:";
}