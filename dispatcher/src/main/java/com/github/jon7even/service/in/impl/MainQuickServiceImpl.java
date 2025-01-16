package com.github.jon7even.service.in.impl;

import com.github.jon7even.service.in.MainQuickService;
import com.github.jon7even.telegram.constants.DefaultBaseMessagesToSend;
import com.github.jon7even.telegram.menu.MainMenu;
import com.github.jon7even.utils.MessageUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;
import java.util.Set;

import static com.github.jon7even.telegram.constants.DefaultMessageLogError.ERROR_TO_EXECUTION_FOR_USER;
import static com.github.jon7even.telegram.constants.DefaultSystemMessagesToSend.ERROR_TO_SEND;

/**
 * Реализация сервиса {@link MainQuickService} быстрой обработки основных команд бота.
 *
 * @author Jon7even
 * @version 2.0
 */
@Slf4j
@Service
@NoArgsConstructor
public class MainQuickServiceImpl implements MainQuickService {

    private static final Set<String> BASE_COMMANDS = new HashSet<>(Set.of(
            MainMenu.START.toString(),
            MainMenu.HELP.toString()
    ));

    @Override
    public boolean existsBaseCommand(String command) {
        return BASE_COMMANDS.contains(command);
    }

    @Override
    public SendMessage processQuickAnswer(Update update) {
        String answer = "";

        switch (update.getMessage().getText()) {
            case "/help" -> answer = DefaultBaseMessagesToSend.HELP_TEXT;
            case "/start" -> answer = DefaultBaseMessagesToSend.START_TEXT;
            default -> {
                answer = String.format("%s, %s", ERROR_TO_SEND, "неправильная логика сервиса обработки сообщений");
                log.error("{} сбой сервиса диспетчера первичной обработки", ERROR_TO_EXECUTION_FOR_USER);
            }
        }
        return MessageUtils.buildAnswerWithMessage(update.getMessage(), answer);
    }
}