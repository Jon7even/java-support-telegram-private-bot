package com.github.jon7even.service.out.client.impl;

import com.github.jon7even.service.out.client.ApiAskClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Реализация сервиса {@link ApiAskClientService}, который отправляет запросы к API нейросетям.
 *
 * @author Jon7even
 * @version 2.0
 */
@Service
@RequiredArgsConstructor
public class ApiAskClientServiceImpl implements ApiAskClientService {

    private final WebClient webClient;

    @Override
    public String sendAskAndGetAnswerFromApi(String request, String URI) {
        return webClient.post()
                .uri(URI)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}