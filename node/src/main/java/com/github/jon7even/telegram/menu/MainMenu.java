package com.github.jon7even.telegram.menu;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MainMenu {
    START("/start"),
    HELP("/help"),
    ITEMS("/items"),
    GIFTS("/gifts");
    private final String command;

    @Override
    public String toString() {
        return command;
    }
}
