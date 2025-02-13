package com.github.jon7even.service.out.client.impl;

import com.github.jon7even.service.out.client.ApiAskClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

/**
 * Реализация сервиса {@link ApiAskClientService}, который отправляет запросы к API нейросетям.
 *
 * @author Jon7even
 * @version 2.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiAskClientServiceImpl implements ApiAskClientService {

    private final WebClient webClient;

    @Override
    public Mono<String> sendAskAndGetAnswerFromApi(String request, String URI) {
        return webClient.post()
                .uri(URI)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> log.info("Ответ от API: {}", response))
                .doOnError(error -> log.error("Ошибка при запросе к API", error));
    }
}