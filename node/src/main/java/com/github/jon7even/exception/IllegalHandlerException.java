package com.github.jon7even.exception;

/**
 * Класс описывающий исключение если не обработана логика нового обработчика.
 *
 * @author Jon7even
 * @version 2.0
 */
public class IllegalHandlerException extends ApplicationException {

    /**
     * Конструктор, принимающий название обработчика.
     *
     * @param className - имя обработчика
     */
    public IllegalHandlerException(String className) {
        super(getErrorMessage(className));
    }

    private static String getErrorMessage(String className) {
        return String.format("В приложение был добавлен новый обработчик [%s], но не распределён в фабрике", className);
    }
}