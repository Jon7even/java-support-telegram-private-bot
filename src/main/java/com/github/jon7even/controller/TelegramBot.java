package com.github.jon7even.controller;

import com.github.jon7even.config.BotConfig;
import com.github.jon7even.model.user.UserEntity;
import com.github.jon7even.repository.GiftRepository;
import com.github.jon7even.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.github.jon7even.model.gift.MenuGift.*;
import static com.github.jon7even.model.menu.MainMenu.*;
import static com.github.jon7even.utils.Emoji.SMAIL;

@Slf4j
@Component
public final class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig config;
    private final UserRepository userRepository;
    private final GiftRepository giftRepository;

    static final String NOT_REALISED = "\uD83D\uDE14 Данная команда находится еще в разработке. \uD83D\uDE14";

    static final String HELP_TEXT = "\n" +
            "Данный виртуальный помощник создан для работников компании.\uD83D\uDE0E \n\n" +
            "На данный момент доступны следующие команды: \uD83D\uDE0F \n\n" +
            START + " - пройти регистрацию  \n\n" +
            GIFTS + " - начать работать с подарками \n\n" +
            HELP + " - доступные команды \n";

    static final String HELP_GIFTS = "\n" +
            "Начинаем работать с подарками, нажмите необходимый пункт меню. \uD83D\uDE0E \n\n" +
            "Помощь с командами: \uD83D\uDE0F \n\n" +
            NEW_COMPANY + " - добавить новую компанию  \n\n" +
            GIVE_GIFT + " - выдать новый подарок \uD83D\uDCA5 \n\n" +
            CALC_GIFTS + " - пересчитать подарки (DANGER!) \n\n" +
            ASSIGN_BY_FORCE + " - назначить подарок вручную \n\n" +
            SEARCH_COMPANY + " - поиск компании \n\n" +
            REMOVE_COMPANY + " - удалить компанию \n\n" +
            LIST_GIFTS + " - кому еще нужно выдать подарки \n\n";

    public TelegramBot(BotConfig config, UserRepository userRepository, GiftRepository giftRepository) {
        super(config.getToken());
        this.config = config;
        this.userRepository = userRepository;
        this.giftRepository = giftRepository;
        List<BotCommand> commandsMenu = new ArrayList<>();
        commandsMenu.add(new BotCommand(START.toString(), "Регистрация"));
        commandsMenu.add(new BotCommand(HELP.toString(), "Список доступных команд"));
        commandsMenu.add(new BotCommand(GIFTS.toString(), "Начать работать с подарками"));
        commandsMenu.add(new BotCommand(ITEMS.toString(), "Начать работать с товарами"));
        log.debug("Меню бота инициализировано");

        try {
            this.execute(new SetMyCommands(commandsMenu, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Что-то пошло не так с загрузкой команд для бота: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String result = update.getMessage().getText();
            Long chaId = update.getMessage().getChatId();

            switch (result) {
                case "/start":
                    registerUser(update.getMessage());
                    startCommandReceived(chaId, update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    prepareAndSendMessage(chaId, HELP_TEXT);
                    break;
                case "/gifts":
                    giftsCommandReceived(chaId);
                    break;
                case "/items":
                    prepareAndSendMessage(chaId, NOT_REALISED);
                    break;
                default:
                    prepareAndSendMessage(chaId, "Такая команда еще не поддерживается нашим ботом \uD83D\uDE31");
                    log.warn("Эту команду мы еще не поддерживаем. Команда пользователя: " + result);
            }
        } else if (update.hasCallbackQuery()) {
            String result = update.getCallbackQuery().getData();
            Long chaId = update.getCallbackQuery().getMessage().getChatId();
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

            switch (result) {
                case "/newcompany":
                    sendEditMessageText("Введите имя компании", chaId, messageId);
                    break;
                case "/givegift":
                    sendEditMessageText("текст", chaId, messageId);
                    break;
                case "/calculation":
                    sendEditMessageText("текст", chaId, messageId);
                    break;
                case "/givemanual":
                    sendEditMessageText("текст", chaId, messageId);
                    break;
                case "/searchcompany":
                    sendEditMessageText("текст", chaId, messageId);
                    break;
                case "/removecompany":
                    sendEditMessageText("текст", chaId, messageId);
                    break;
                case "/checkgifts":
                    sendEditMessageText("текст", chaId, messageId);
                    break;
                default:
                    prepareAndSendMessage(chaId, "Такая команда еще не поддерживается нашим ботом \uD83D\uDE31");
                    log.warn("Эту команду мы еще не поддерживаем. Команда пользователя: " + result);
            }
        } else {
            log.error("Произошла странная ошибка: {}", update);
        }

    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Приветствую тебя, " + name + SMAIL;
        prepareAndSendMessage(chatId, answer);
    }

    private void prepareAndSendMessage(Long chatId, String textToSend) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(textToSend)
                .build();

        sendMessageText(message);
    }

    private void sendEditMessageText(String text, Long chatId, Integer messageId) {
        EditMessageText message = EditMessageText.builder()
                .chatId(chatId)
                .text(text)
                .messageId(Math.toIntExact(messageId))
                .build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Произошла какая-то ошибка: " + e.getMessage());
        }
    }

    private void sendMessageText(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Произошла какая-то ошибка: " + e.getMessage());
        }
    }

    private void registerUser(Message message) {
        var chatId = message.getChatId();

        if (userRepository.existsByChatId(chatId)) {
            log.info("Это наш старожил, пользователь уже есть в системе.");
            log.debug("Это наш старожил, пользователь уже есть в системе с tgId={}", chatId);
        } else {
            log.info("Начинаю регистрацию нового пользователя");
            log.debug("Начинаю регистрацию нового пользователя с tgId={}", chatId);

            var chat = message.getChat();
            UserEntity user = UserEntity.builder()
                    .chatId(chatId)
                    .firstName(chat.getFirstName())
                    .lastName(chat.getLastName())
                    .userName(chat.getUserName())
                    .registeredOn(LocalDateTime.now())
                    .build();
            log.debug("Новый пользователь собран user={}", user);

            userRepository.save(user);
            log.debug("Пользователь успешно сохранен в БД");
        }
    }

    private void giftsCommandReceived(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(HELP_GIFTS)
                .build();

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLineOne = new ArrayList<>();
        List<InlineKeyboardButton> rowInLineSecond = new ArrayList<>();
        List<InlineKeyboardButton> rowInLineThird = new ArrayList<>();
        List<InlineKeyboardButton> rowInLineFourth = new ArrayList<>();

        var newCompany = new InlineKeyboardButton();
        newCompany.setText(NEW_COMPANY.toString());
        newCompany.setCallbackData(NEW_COMPANY.toString());
        rowInLineOne.add(newCompany);

        var giveGift = new InlineKeyboardButton();
        giveGift.setText(GIVE_GIFT.toString());
        giveGift.setCallbackData(GIVE_GIFT.toString());
        rowInLineOne.add(giveGift);

        var calculationGifts = new InlineKeyboardButton();
        calculationGifts.setText(CALC_GIFTS.toString());
        calculationGifts.setCallbackData(CALC_GIFTS.toString());
        rowInLineSecond.add(calculationGifts);

        var giveManual = new InlineKeyboardButton();
        giveManual.setText(ASSIGN_BY_FORCE.toString());
        giveManual.setCallbackData(ASSIGN_BY_FORCE.toString());
        rowInLineSecond.add(giveManual);

        var searchCompany = new InlineKeyboardButton();
        searchCompany.setText(SEARCH_COMPANY.toString());
        searchCompany.setCallbackData(SEARCH_COMPANY.toString());
        rowInLineThird.add(searchCompany);

        var removeCompany = new InlineKeyboardButton();
        removeCompany.setText(REMOVE_COMPANY.toString());
        removeCompany.setCallbackData(REMOVE_COMPANY.toString());
        rowInLineThird.add(removeCompany);

        var listGifts = new InlineKeyboardButton();
        listGifts.setText(LIST_GIFTS.toString());
        listGifts.setCallbackData(LIST_GIFTS.toString());
        rowInLineFourth.add(listGifts);

        rowsInLine.add(rowInLineOne);
        rowsInLine.add(rowInLineSecond);
        rowsInLine.add(rowInLineThird);
        rowsInLine.add(rowInLineFourth);
        markup.setKeyboard(rowsInLine);
        message.setReplyMarkup(markup);

        sendMessageText(message);
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

}
