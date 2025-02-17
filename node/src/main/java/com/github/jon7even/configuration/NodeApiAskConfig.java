package com.github.jon7even.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("node.api-ask")
public class NodeApiAskConfig {

    /**
     * Адрес сервиса, который предоставляет API нейросети
     */
    private String url;

    /**
     * Таймаут для установления соединения с сервисом в миллисекундах
     */
    private int timeoutConnect;

    /**
     * Общий таймаут соединения с сервисом в миллисекундах(включая установление соединения и чтение)
     */
    private int timeoutResponse;

    /**
     * Таймаут получения(скачивания) данных в миллисекундах(включая установление соединения)
     */
    private int timeoutRead;

    /**
     * Таймаут ожидания освобождения потока из пула
     */
    private int timeoutPool;

    /**
     * Максимальное количество потоков для асинхронной передачи данных
     */
    private int maxConnectionsPool;
}