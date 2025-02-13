package com.github.jon7even.service.out.client;

import reactor.core.publisher.Mono;

/**
 * Интерфейс сервиса для делегирования отправки запросов и получения ответов от сервиса,
 * который предоставляет API к нейросетям.
 *
 * @author Jon7even
 * @version 2.0
 */
public interface ApiAskClientService {

    /**
     * Метод для отправки запроса нейросети и получения от неё ответа.
     *
     * @param request текст с вопросом
     * @param URI     эндпоинт API
     * @apiNote предполагается, что эндпоинты могут быть разными. Например - разные модели нейросетей.
     */
    Mono<String> sendAskAndGetAnswerFromApi(String request, String URI);
}