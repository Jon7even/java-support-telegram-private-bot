package com.github.jon7even.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateProducerService {
    void produceText(String rabbitQueue, Update update);
    void produceCallBackQuery(String rabbitQueue, Update update);
}
