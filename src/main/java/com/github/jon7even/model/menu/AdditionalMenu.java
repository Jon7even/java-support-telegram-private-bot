package com.github.jon7even.model.menu;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AdditionalMenu {
    LIST_ITEMS("/checkitems");
    private final String command;

    @Override
    public String toString() {
        return command;
    }
}
