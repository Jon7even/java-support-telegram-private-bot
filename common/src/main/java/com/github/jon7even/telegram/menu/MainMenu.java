package com.github.jon7even.telegram.menu;

import lombok.AllArgsConstructor;

/**
 * Перечисление основных команд главного меню чат-бота
 *
 * @author Jon7even
 * @version 1.0
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
     * Инициализирует работу с товарами
     */
    ITEMS("/items"),

    /**
     * Инициализирует работу с задачами
     */
    TASKS("/tasks"),

    /**
     * Инициализирует работу с конкурентами
     */
    COMPETITORS("/competitors"),

    /**
     * Инициализирует работу с подарками
     */
    GIFTS("/gifts");

    private final String command;

    @Override
    public String toString() {
        return command;
    }
}
