package com.github.jon7even.service;

import com.github.jon7even.telegram.menu.MainMenu;
import com.github.jon7even.utils.MessageUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.jon7even.telegram.constants.BaseMessages.HELP_TEXT;
import static com.github.jon7even.telegram.constants.BaseMessages.START_TEXT;

@Slf4j
@Service
@NoArgsConstructor
public class MainQuickServiceImpl implements MainQuickService {
    @Override
    public boolean existBaseCommand(String command) {
        return command.equals(MainMenu.START.toString()) || command.equals(MainMenu.HELP.toString());
    }

    @Override
    public SendMessage processQuickAnswer(Update update) {
        String resultMessage = update.getMessage().getText();

        String answer = "";

        switch (resultMessage) {
            case "/start":
                answer = START_TEXT;
                break;
            case "/help":
                answer = HELP_TEXT;
                break;
            default:
                log.error("Произошел сбой сервиса диспетчера первичной обработки!");
        }
        return MessageUtils.buildAnswerWithMessage(update.getMessage(), answer);
    }

}
