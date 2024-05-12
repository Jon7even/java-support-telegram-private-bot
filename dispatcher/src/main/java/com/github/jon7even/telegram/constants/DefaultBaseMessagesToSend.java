package com.github.jon7even.telegram.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.github.jon7even.telegram.menu.MainMenu.*;

/**
 * Утилитарный класс хранящий текст для ответов на основные команды (/start и /help)
 *
 * @author Jon7even
 * @version 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultBaseMessagesToSend {
    public static final String START_TEXT = "\n" +
            "Добро пожаловать в нашу команду! \uD83E\uDD73 \n\n" +
            "Данный виртуальный помощник создан для работников нашей компании.\uD83D\uDE0E \n\n" +
            "Введите команду, чтобы начать работать, например: \n\n" +
            HELP + " - отобразит все доступные команды нашего бота \n";

    public static final String HELP_TEXT = "\n" +
            "На данный момент доступны следующие команды: \uD83D\uDE0F\uD83D\uDE0F\uD83D\uDE0F\n\n" +
            HELP + " - все доступные команды \n\n" +
            ITEMS + " - работа с товарами \n\n" +
            TASKS + " - работа с задачами \n\n" +
            COMPETITORS + " - работа с конкурентами \n\n" +
            GIFTS + " - работа с подарками \n\n";
}
