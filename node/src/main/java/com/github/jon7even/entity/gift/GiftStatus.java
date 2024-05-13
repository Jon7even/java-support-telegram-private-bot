package com.github.jon7even.entity.gift;

/**
 * Перечисление возможных статусов подарков
 *
 * @author Jon7even
 * @version 1.0
 */
public enum GiftStatus {
    /**
     * Подарки есть в наличии их можно дарить
     */
    ACTIVATED,

    /**
     * Подарки закончились, их невозможно подарить
     */
    DEACTIVATED
}
