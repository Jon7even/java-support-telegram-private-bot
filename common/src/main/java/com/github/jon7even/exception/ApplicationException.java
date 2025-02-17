package com.github.jon7even.exception;

import lombok.Getter;

/**
 * Подкласс-шаблон для наследуемых исключений.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Исключения желательно наследовать от этого класса для лучшей унификации.
 */
@Getter
public class ApplicationException extends RuntimeException {

    private final String errorMessage;

    /**
     * Конструктор, который принимает сообщение с ошибкой.
     *
     * @param errorMessage - сообщение об ошибке
     */
    public ApplicationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}