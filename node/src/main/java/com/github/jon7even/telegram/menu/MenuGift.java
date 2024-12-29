package com.github.jon7even.telegram.menu;

import lombok.AllArgsConstructor;

/**
 * Перечисление возможных команд меню Подарки(GIFTS)
 *
 * @author Jon7even
 * @version 1.0
 */
@AllArgsConstructor
public enum MenuGift {
    /**
     * Добавить новую компанию
     */
    NEW_COMPANY("/newcompany"),

    /**
     * Выдать подарок компании из имеющихся
     */
    GIVE_GIFT("/givegift"),

    /**
     * Выдать подарок принудительно
     */
    ASSIGN_BY_FORCE("/givemanual"),

    /**
     * Поиск компании
     */
    SEARCH_COMPANY("/searchcompany"),

    /**
     * Удалить компанию
     */
    REMOVE_COMPANY("/removecompany"),

    /**
     * Список доступных подарков
     */
    LIST_GIFTS("/checkgifts");
    private final String command;

    @Override
    public String toString() {
        return command;
    }
}
