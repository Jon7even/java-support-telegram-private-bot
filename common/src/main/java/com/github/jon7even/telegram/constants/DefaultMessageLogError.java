package com.github.jon7even.telegram.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс для типовых служебных сообщений, которые отправляются пользователю при ошибках сервиса
 *
 * @author Jon7even
 * @version 2.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultMessageLogError {

    public static final String ERROR_TO_EXECUTION_FOR_USER = "Произошла ошибка в процессе ответа пользователю Error:";

    public static final String ERROR_COMMAND_NOT_SUPPORT = "Эту команду мы еще не поддерживаем. Команда пользователя: ";
}