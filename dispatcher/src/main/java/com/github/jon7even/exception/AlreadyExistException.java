package com.github.jon7even.exception;

/**
 * Класс описывающий исключение, если сущность не создана потому что уже существует.
 *
 * @author Jon7even
 * @version 2.0
 */
public class AlreadyExistException extends ApplicationException {

    /**
     * Конструктор, принимающий название ресурса который уже существует.
     *
     * @param resource - сообщение об ошибке
     */
    public AlreadyExistException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] already exist", resource);
    }
}