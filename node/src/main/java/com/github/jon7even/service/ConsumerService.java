package com.github.jon7even.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ConsumerService {
    void consumeTextMessageUpdates(Update update);

    void consumeDocMessageUpdates(Update update);

    void consumeAudioMessageUpdates(Update update);

    void consumePhotoMessageUpdates(Update update);
}
