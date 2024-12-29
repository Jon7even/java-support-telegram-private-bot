package com.github.jon7even.exception;

/**
 * Класс описывающий исключение если ресурс был не найден
 *
 * @author Jon7even
 * @version 2.0
 */
public class NotFoundException extends ApplicationException {

    public NotFoundException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] not found", resource);
    }
}