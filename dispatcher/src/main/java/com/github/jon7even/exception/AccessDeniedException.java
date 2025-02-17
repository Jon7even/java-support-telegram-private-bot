package com.github.jon7even.exception;

/**
 * Класс описывающий исключение, если доступ к ресурсу запрещен.
 *
 * @author Jon7even
 * @version 2.0
 */
public class AccessDeniedException extends ApplicationException {

    /**
     * Конструктор, принимающий название ресурса к которому запрещен доступ.
     *
     * @param resource - сообщение об ошибке
     */
    public AccessDeniedException(String resource) {
        super(getErrorMessage(resource));
    }

    private static String getErrorMessage(String resource) {
        return String.format("[%s] Access Denied", resource);
    }
}