package com.github.jon7even.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DefaultMessageError {
    public static final String ERROR_TO_SEND = "Извините, произошла ошибка: ";

    public static final String ERROR_SEND_TEXT = "что-то произошло с сервисом ответов на сообщения, " +
            "свяжитесь пожалуйста с администратором";

    public static final String ERROR_RECEIVE = "что-то произошло с сервисом обработки сообщений, " +
            "свяжитесь пожалуйста с администратором";
}
