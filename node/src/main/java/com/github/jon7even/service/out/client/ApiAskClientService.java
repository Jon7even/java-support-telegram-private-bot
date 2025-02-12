package com.github.jon7even.service.out.client;

/**
 * Интерфейс сервиса для делегирования отправки запросов и получения ответов от сервиса,
 * который предоставляет API к нейросетям.
 *
 * @author Jon7even
 * @version 2.0
 */
public interface ApiAskClientService {

    /**
     * Метод для отправки запроса нейросети и получения ответа.
     *
     * @param request текст с вопросом
     */
    String sendAskAndGetAnswerFromApi(String request);
}