package com.github.jon7even.telegram;

/**
 * Перечисление текущего состояния бота для конкретного пользователя
 *
 * @author Jon7even
 * @version 2.0
 */
public enum BotState {
    /**
     * Статусы главного меню
     */
    MAIN_START,
    MAIN_HELP,
    MAIN_ITEMS,
    MAIN_GIFTS,

    /**
     * Статусы добавления новой компании
     */
    COMPANY_NAME,
    COMPANY_SUM,
    COMPANY_TYPE_GIFT,
    COMPANY_IS_GIVEN,
    COMPANY_IS_DONE;
}