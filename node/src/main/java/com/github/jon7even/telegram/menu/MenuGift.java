package com.github.jon7even.telegram.menu;

import lombok.AllArgsConstructor;

/**
 * Перечисление возможных команд меню Подарки(GIFTS)
 *
 * @author Jon7even
 * @version 2.0
 */
@Deprecated
@AllArgsConstructor
public enum MenuGift {

    /**
     * Выдать подарок компании
     */
    GIVE_GIFT("/givegift"),

    /**
     * Забрать подарок у компании обратно в случае ошибки
     */
    REMOVE_GIFT("/removegift"),

    /**
     * Список выданных подарков
     */
    LIST_GIFTS("/checkgifts");

    private final String command;

    @Override
    public String toString() {
        return command;
    }
}