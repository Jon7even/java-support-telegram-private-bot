package com.github.jon7even.exception;

import lombok.Getter;

/**
 * Подкласс-шаблон для наследуемых исключений
 *
 * @author Jon7even
 * @version 2.0
 */
@Getter
public class ApplicationException extends RuntimeException {

    private final String errorMessage;

    public ApplicationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}