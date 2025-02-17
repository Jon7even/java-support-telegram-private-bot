package com.github.jon7even;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Основной класс запуска сервиса "Node - Узел", управляется фреймворком SpringBoot.
 *
 * @author Jon7even
 * @version 2.0
 */
@SpringBootApplication
public class NodeApp {

    public static void main(String[] args) {
        SpringApplication.run(NodeApp.class);
    }

}