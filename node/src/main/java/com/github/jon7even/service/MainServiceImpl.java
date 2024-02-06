package com.github.jon7even.service;

import com.github.jon7even.cache.UserDataCache;
import com.github.jon7even.model.company.CompanyBuildingDto;
import com.github.jon7even.model.company.CompanyEntity;
import com.github.jon7even.model.user.UserEntity;
import com.github.jon7even.repository.CompanyRepository;
import com.github.jon7even.repository.UserRepository;
import com.github.jon7even.telegram.BotState;
import com.github.jon7even.telegram.menu.gift.TypeGift;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.github.jon7even.telegram.menu.MainMenu.*;
import static com.github.jon7even.telegram.menu.gift.MenuGift.*;
import static com.github.jon7even.utils.Emoji.SMAIL;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UserDataCache userDataCache;
    private final ProducerService producerService;
    private final ReplyMessageService replyMessageService;

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

    @Override
    public void processTextMessage(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String resultMessage = update.getMessage().getText();
            Long chaId = update.getMessage().getChatId();

            BotState botState = userDataCache.getBotStateFromCache(chaId);
            log.debug("Текущее состояние бота у пользователя: {}", botState);
            CompanyBuildingDto companyBuildingDto = userDataCache.getCompanyFromCache(chaId);
            log.debug("Текущий конструктор компании: {}", companyBuildingDto);

            if (botState.equals(BotState.COMPANY_NAME)) {
                companyBuildingDto.setNameCompany(resultMessage);
                userDataCache.setBotStateForCacheUser(chaId, BotState.COMPANY_SUM);
                prepareAndSendMessage(chaId, "Отлично, а теперь укажите сумму за год");
            }

            if (botState.equals(BotState.COMPANY_SUM)) {
                try {
                    companyBuildingDto.setTotalSum(Integer.valueOf(resultMessage));
                } catch (NumberFormatException e) {
                    log.warn("Пользователь шалит и передает в строку нечисловое значение. {}", e.getMessage());
                    companyBuildingDto.setTotalSum(50000);
                    prepareAndSendMessage(
                            chaId, "Вы указали не число, поэтому мы поставили дефолтное значение 50 000 руб."
                    );
                }

                userDataCache.setBotStateForCacheUser(chaId, BotState.COMPANY_TYPE_GIFT);
                prepareAndSendMessage(chaId, "Укажите тип подарка - Стандарт или Премиум");
            }

            if (botState.equals(BotState.COMPANY_TYPE_GIFT)) {
                if (resultMessage.equals("Стандарт")) {
                    companyBuildingDto.setType(TypeGift.LIGHT);
                } else if (resultMessage.equals("Премиум")) {
                    companyBuildingDto.setType(TypeGift.PREMIUM);
                } else {
                    prepareAndSendMessage(chaId, "Что-то пошло не так, ставим, что подарок стандарт...");
                    companyBuildingDto.setType(TypeGift.LIGHT);
                }
                userDataCache.setBotStateForCacheUser(chaId, BotState.COMPANY_IS_GIVEN);
                prepareAndSendMessage(chaId, "Подарок уже выдан? Отвечайте Да или Нет.");
            }

            if (botState.equals(BotState.COMPANY_IS_GIVEN)) {
                if (resultMessage.equals("Да")) {
                    companyBuildingDto.setIsGiven(true);
                } else if (resultMessage.equals("Нет")) {
                    companyBuildingDto.setIsGiven(false);
                } else {
                    companyBuildingDto.setIsGiven(false);
                    prepareAndSendMessage(chaId, "Вы что-то не то нажали. Считаем, что подарок не выдан.");
                }
                userDataCache.setBotStateForCacheUser(chaId, BotState.COMPANY_IS_DONE);
                prepareAndSendMessage(chaId, "Отлично! Сейчас будем сохранять в базу \uD83E\uDD73");

                UserEntity userCreator = userRepository.findByChatId(chaId);
                log.debug("Проверяем, что пользователь есть в системе: {}", userCreator);

                log.debug("Текущая DTO компании: {}", companyBuildingDto);
                CompanyEntity company = CompanyEntity.builder()
                        .nameCompany(companyBuildingDto.getNameCompany())
                        .totalSum(companyBuildingDto.getTotalSum())
                        .type(companyBuildingDto.getType())
                        .isGiven(companyBuildingDto.getIsGiven())
                        .creator(userCreator)
                        .created(LocalDateTime.now())
                        .given(LocalDateTime.now())
                        .build();
                log.debug("Сконструирована новая компания: {}", company);

                CompanyEntity createdCompany = companyRepository.save(company);
                log.trace("Пользователь с id={} добавил новую компанию: {}", userCreator.getChatId(), company);

                userDataCache.setBotStateForCacheUser(chaId, BotState.MAIN_HELP);
                prepareAndSendMessage(chaId, String.format("Вы успешно добавили компанию: \n\n" +
                                "%s с годовым оборотом: %d руб. выбранный подарок: %s",
                        createdCompany.getNameCompany(), createdCompany.getTotalSum(),
                        createdCompany.getType().toString()));
            }

            userDataCache.setCompanyForCacheUser(chaId, companyBuildingDto);

            switch (resultMessage) {
                case "/gifts":
                    giftsCommandReceived(chaId);
                    userDataCache.setBotStateForCacheUser(chaId, BotState.MAIN_GIFTS);
                    break;
                default:
                    if (botState.equals(BotState.MAIN_HELP) || botState.equals(BotState.MAIN_START) ||
                            botState.equals(BotState.MAIN_GIFTS)) {
                        prepareAndSendMessage(chaId, replyMessageService.getReplyText("reply.nonsupport"));
                        userDataCache.setBotStateForCacheUser(chaId, BotState.MAIN_HELP);
                        log.warn("Эту команду мы еще не поддерживаем. Команда пользователя: " + resultMessage);
                    } else {
                        log.debug("Текущий статус пользователя: {}", botState);
                        log.debug("Текущий конструктор компании: {}", companyBuildingDto);
                    }
            }
        } else {
            log.error("Произошла неизвестная ошибка: {}", update);
        }
    }

    @Override
    public void processCallbackQuery(Update update) {
        if (update.hasCallbackQuery()) {
            String result = update.getCallbackQuery().getData();
            Long chaId = update.getCallbackQuery().getMessage().getChatId();
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

            switch (result) {
                case "/newcompany":
                    sendEditMessageText("Начинаем процесс добавления новой компании.\n\n " +
                            "Давайте начнем с названия компании, введите его:\n\n", chaId, messageId);
                    userDataCache.setBotStateForCacheUser(chaId, BotState.COMPANY_NAME);
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
                    sendEditMessageText("Общий список компаний: ", chaId, messageId);
                    processGetListCompanies(chaId);
                    break;
                default:
                    prepareAndSendMessage(chaId, replyMessageService.getReplyText("reply.nonsupport"));
                    log.warn("Эту команду мы еще не поддерживаем. Команда пользователя: " + result);
            }
        } else {
            log.error("Произошла неизвестная ошибка: {}", update);
        }
    }

    private void processGetListCompanies(Long chaId) {
        List<CompanyEntity> listCompanies = companyRepository.findAll();
        log.debug("Список компаний: {}", listCompanies);
        String answer = StringUtils.join(listCompanies);
        prepareAndSendMessage(chaId, answer);
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
        EditMessageText messageToEdit = EditMessageText.builder()
                .chatId(chatId)
                .text(text)
                .messageId(Math.toIntExact(messageId))
                .build();

        log.debug("Отвечаем на сообщение sendMessage={}", messageToEdit);
        producerService.producerAnswerEditText(messageToEdit);
    }

    private void sendMessageText(SendMessage message) {
        producerService.producerAnswerText(message);
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

}
