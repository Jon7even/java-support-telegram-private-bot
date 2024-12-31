package com.github.jon7even.telegram;

/**
 * Перечисление текущего состояния бота для конкретного пользователя
 *
 * @author Jon7even
 * @version 2.0
 */
public enum BotState {

    /**
     * Статус главного меню
     */
    MAIN_START,

    /**
     * Статус меню помощи
     */
    MAIN_HELP,

    /**
     * Статус меню подарков
     */
    MAIN_GIFTS,

    /**
     * Статус меню работы с нейросетями
     */
    MAIN_ASK;
}