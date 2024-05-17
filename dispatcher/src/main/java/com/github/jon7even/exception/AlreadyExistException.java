package com.github.jon7even.exception;

/**
 * Класс описывающий исключение если сущность не создана потому что уже существует
 *
 * @author Jon7even
 * @version 1.0
 */
public class AlreadyExistException extends ApplicationException {
    public AlreadyExistException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] already exist", resource);
    }
}

