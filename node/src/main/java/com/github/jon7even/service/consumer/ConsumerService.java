package com.github.jon7even.service.consumer;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ConsumerService {
    void consumeTextMessageUpdates(Update update);

    void consumeCallbackQueryUpdates(Update update);

    void consumeDocMessageUpdates(Update update);

    void consumeAudioMessageUpdates(Update update);

    void consumePhotoMessageUpdates(Update update);
}
