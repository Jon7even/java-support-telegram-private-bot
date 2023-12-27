package com.github.jon7even.telegram.menu.gift;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MenuGift {
    NEW_COMPANY("/newcompany"),
    GIVE_GIFT("/givegift"),
    CALC_GIFTS("/calculation"),
    ASSIGN_BY_FORCE("/givemanual"),
    SEARCH_COMPANY("/searchcompany"),
    REMOVE_COMPANY("/removecompany"),
    LIST_GIFTS("/checkgifts");
    private final String command;

    @Override
    public String toString() {
        return command;
    }
}
