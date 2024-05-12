package com.github.jon7even;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс запуска Диспетчера, управляется фреймворком SpringBoot
 *
 * @author Jon7even
 * @version 1.0
 */
@SpringBootApplication
public class DispatcherApp {
    public static void main(String[] args) {
        SpringApplication.run(DispatcherApp.class);
    }
}
