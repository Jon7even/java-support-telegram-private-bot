package com.github.jon7even.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Базовая конфигурация reactive клиента обращающегося по API к сервису, который предоставляет доступ к нейросетям.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Это не финальная конфигурация, а её тело, которое не меняется. В самом сервисе дополнительные настройки.
 */
@Configuration
@RequiredArgsConstructor
public class ApiWebClientConfig {

    private final NodeApiAskConfig config;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {

        ConnectionProvider customConnectionProvider = ConnectionProvider.builder("customConnectionProvider")
                .maxConnections(config.getMaxConnectionsPool())
                .pendingAcquireTimeout(Duration.ofMillis(config.getTimeoutPool()))
                .maxIdleTime(Duration.ofMinutes(1))
                .maxLifeTime(Duration.ofMinutes(10))
                .evictInBackground(Duration.ofMinutes(5))
                .build();

        return builder
                .baseUrl(config.getUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create(customConnectionProvider)
                                .responseTimeout(Duration.ofMillis(config.getTimeoutResponse()))
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.getTimeoutConnect())
                                .doOnConnected(connection -> connection.addHandlerLast(
                                                new ReadTimeoutHandler(config.getTimeoutRead(), TimeUnit.MILLISECONDS)
                                        )
                                )
                                .wiretap(
                                        "reactor.netty.http.client.HttpClient",
                                        LogLevel.DEBUG,
                                        AdvancedByteBufFormat.TEXTUAL
                                )
                ))
                .build();
    }
}