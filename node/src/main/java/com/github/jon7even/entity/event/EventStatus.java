package com.github.jon7even.entity.event;

/**
 * Перечисление возможных статусов событий
 *
 * @author Jon7even
 * @version 1.0
 */
public enum EventStatus {
    /**
     * Событие активно и по нему можно дарить подарки
     */
    ACTIVATED,

    /**
     * Событие прошло и по нему нельзя вручить подарки
     */
    DEACTIVATED
}
