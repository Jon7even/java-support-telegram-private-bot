package com.github.jon7even.telegram.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.github.jon7even.telegram.menu.MainMenu.ASK;
import static com.github.jon7even.telegram.menu.MainMenu.GIFTS;
import static com.github.jon7even.telegram.menu.MainMenu.HELP;

/**
 * Утилитарный класс хранящий текст для ответов на основные команды (/start и /help)
 *
 * @author Jon7even
 * @version 2.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultBaseMessagesToSend {

    public static final String START_TEXT = "\n"
            + "Добро пожаловать в нашу команду! \uD83E\uDD73 \n\n"
            + "Данный виртуальный помощник создан для работников нашей компании.\uD83D\uDE0E \n\n"
            + "Введите команду, чтобы начать работать, например: \n\n"
            + HELP + " - отобразит все доступные команды нашего бота \n";

    public static final String HELP_TEXT = "\n" +
            "На данный момент доступны следующие команды: \uD83D\uDE0F\uD83D\uDE0F\uD83D\uDE0F\n\n"
            + HELP + " - все доступные команды \n\n"
            + GIFTS + " - работа с подарками \n\n"
            + ASK + " - работа с нейросетью \n\n";

    public static final String USER_AUTH_TRUE = "\n"
            + "Поздравляем! Вы успешно авторизовались \n\n"
            + "Добро пожаловать в нашу команду! \uD83E\uDD73 \n\n";
}