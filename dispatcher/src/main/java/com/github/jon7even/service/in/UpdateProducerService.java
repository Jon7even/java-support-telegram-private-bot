package com.github.jon7even.service.in;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateProducerService {
    void produceText(String rabbitQueue, Update update);
    void produceCallBackQuery(String rabbitQueue, Update update);
}
