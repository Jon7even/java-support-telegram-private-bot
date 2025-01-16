package com.github.jon7even.telegram.constants;

import com.github.jon7even.telegram.menu.MainMenu;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Утилитарный класс хранящий текст для ответов на основные команды.
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
            + MainMenu.HELP + " - отобразит все доступные команды нашего бота \n";

    public static final String HELP_TEXT = "\n" +
            "На данный момент доступны следующие команды: \uD83D\uDE0F\uD83D\uDE0F\uD83D\uDE0F\n\n"
            + MainMenu.HELP + " - все доступные команды \n\n"
            + MainMenu.GIFTS + " - работа с подарками \n\n"
            + MainMenu.ASK + " - работа с нейросетью \n\n";

    public static final String USER_AUTH_TRUE = "\n"
            + "Поздравляем! Вы успешно авторизовались \n\n"
            + "Добро пожаловать в нашу команду! \uD83E\uDD73 \n\n";
}