package com.github.jon7even.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

/**
 * Утилитарный класс для работы с парсингом времени
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Можно использовать в разных частях приложения, в т.ч. тестах
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataTimePattern {
    public static final String DATE_TIME_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_DEFAULT);
}