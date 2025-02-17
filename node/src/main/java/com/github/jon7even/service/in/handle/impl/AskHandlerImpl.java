package com.github.jon7even.service.in.handle.impl;

import com.github.jon7even.service.in.handle.UserHandlerService;
import com.github.jon7even.service.in.status.UserStatusService;
import com.github.jon7even.service.out.producer.SenderMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Реализация обработчика {@link UserHandlerService} для работы с нейростью.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote Используется API нейросети, которая работает с запросами. Можно расширить класс и задать дополнительные
 * команды, настройки и возможности. Сейчас работает все просто: приходит запрос от пользователя мы отправляем его
 * боту -> бот отвечает -> скидываем ответ пользователю от бота.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AskHandlerImpl implements UserHandlerService {

    private final SenderMessageService senderMessageService;

    private final UserStatusService userStatusService;

    @Override
    public void handle(Update update) {
        String request = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        log.info("Начинаю отправлять запрос [request={}] пользователя c [chatId={}] ", request, chatId);

        String answerFromAPI = "ответ нейросети";

        senderMessageService.sendText(chatId, answerFromAPI);
    }
}