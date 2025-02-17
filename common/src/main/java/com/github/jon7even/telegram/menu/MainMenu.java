package com.github.jon7even.telegram.menu;

import lombok.AllArgsConstructor;

/**
 * Перечисление основных команд главного меню чат-бота.
 *
 * @author Jon7even
 * @version 2.0
 */
@AllArgsConstructor
public enum MainMenu {

    /**
     * Регистрация и инициализация пользователя
     */
    START("/start"),

    /**
     * Выводит помощь по доступным командам
     */
    HELP("/help"),

    /**
     * Инициализирует работу с подарками
     */
    GIFTS("/gifts"),

    /**
     * Инициализирует работу с подключенной нейросетью
     */
    ASK("/ask");

    private final String command;

    @Override
    public String toString() {
        return command;
    }
}