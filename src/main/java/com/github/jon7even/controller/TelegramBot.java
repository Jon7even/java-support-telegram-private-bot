package com.github.jon7even.controller;

import com.github.jon7even.cache.UserDataCache;
import com.github.jon7even.config.BotConfig;
import com.github.jon7even.model.company.CompanyBuildingDto;
import com.github.jon7even.model.company.CompanyEntity;
import com.github.jon7even.model.user.UserEntity;
import com.github.jon7even.repository.CompanyRepository;
import com.github.jon7even.repository.UserRepository;
import com.github.jon7even.telegram.BotState;
import com.github.jon7even.telegram.menu.gift.TypeGift;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

import static com.github.jon7even.telegram.menu.MainMenu.*;
import static com.github.jon7even.telegram.menu.gift.MenuGift.*;
import static com.github.jon7even.utils.Emoji.SMAIL;

@Slf4j
@Component
public final class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig config;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UserDataCache userDataCache;

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

    public TelegramBot(BotConfig config, UserRepository userRepository, CompanyRepository companyRepository,
                       UserDataCache userDataCache) {
        super(config.getToken());
        this.config = config;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.userDataCache = userDataCache;
        List<BotCommand> commandsMenu = new ArrayList<>();
        commandsMenu.add(new BotCommand(START.toString(), "Регистрация"));
        commandsMenu.add(new BotCommand(HELP.toString(), "Список доступных команд"));
        commandsMenu.add(new BotCommand(GIFTS.toString(), "Начать работать с подарками"));
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
            String resultMessage = update.getMessage().getText();
            Long chaId = update.getMessage().getChatId();
            UserEntity userFromBD = registerUser(update.getMessage());

            if (processAuthorization(chaId, userFromBD)) {
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
                    case "/start":
                        UserEntity userEntity = registerUser(update.getMessage());
                        processAuthorization(chaId, userEntity);
                        startCommandReceived(chaId, update.getMessage().getChat().getFirstName());
                        userDataCache.setBotStateForCacheUser(chaId, BotState.MAIN_START);
                        break;
                    case "/help":
                        prepareAndSendMessage(chaId, HELP_TEXT);
                        userDataCache.setBotStateForCacheUser(chaId, BotState.MAIN_HELP);
                        break;
                    case "/gifts":
                        giftsCommandReceived(chaId);
                        userDataCache.setBotStateForCacheUser(chaId, BotState.MAIN_GIFTS);
                        break;
                    default:
                        if (botState.equals(BotState.MAIN_HELP) || botState.equals(BotState.MAIN_START) ||
                                botState.equals(BotState.MAIN_GIFTS)) {
                            prepareAndSendMessage(chaId, "Такая команда еще не поддерживается нашим ботом \uD83D\uDE31");
                            userDataCache.setBotStateForCacheUser(chaId, BotState.MAIN_HELP);
                            log.warn("Эту команду мы еще не поддерживаем. Команда пользователя: " + resultMessage);
                        } else {
                            log.debug("Текущий статус пользователя: {}", botState);
                            log.debug("Текущий конструктор компании: {}", companyBuildingDto);
                        }
                }
            } else {
                processInputPassword(resultMessage, chaId, userFromBD);
            }

        } else if (update.hasCallbackQuery()) {
            String result = update.getCallbackQuery().getData();
            Long chaId = update.getCallbackQuery().getMessage().getChatId();
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            UserEntity userFromBD = registerUser(update.getCallbackQuery().getMessage());

            if (processAuthorization(chaId, userFromBD)) {
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
                        prepareAndSendMessage(chaId, "Такая команда еще не поддерживается нашим ботом \uD83D\uDE31");
                        log.warn("Эту команду мы еще не поддерживаем. Команда пользователя: " + result);
                }
            } else {
                log.error("Пользователь user={} смог зайти в дополнительное меню без авторизации", userFromBD);
            }

        } else {
            log.error("Произошла странная ошибка: {}", update);
        }

    }

    private void processGetListCompanies(Long chaId) {
        List<CompanyEntity> listCompanies = companyRepository.findAll();
        log.debug("Список компаний: {}", listCompanies);
        String answer = StringUtils.join(listCompanies);
        prepareAndSendMessage(chaId, answer);

    }

    private void processInputPassword(String resultMessage, Long chaId, UserEntity userEntity) {
        int pass = 0;

        try {
            pass = Integer.parseInt(resultMessage);
        } catch (NumberFormatException e) {
            log.warn("Пользователь пытается ввести пароль, но передает туда не число {}", e.getMessage());
        }

        if (pass == Integer.parseInt(this.config.getPass())) {
            userEntity.setAuthorization(true);
            log.debug("Пользователь ввел пароль, сохраняем в базу user={}", userEntity);
            userRepository.save(userEntity);
            log.trace("Пользователь user={} прошел авторизацию", userEntity);
        } else {
            log.warn("Пользователь user={} пытается подобрать пароль", userEntity);
            prepareAndSendMessage(chaId, "Такая команда еще не поддерживается нашим ботом \uD83D\uDE31");
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
        EditMessageText messageToEdit = EditMessageText.builder()
                .chatId(chatId)
                .text(text)
                .messageId(Math.toIntExact(messageId))
                .build();
        try {
            execute(messageToEdit);
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

    private Boolean processAuthorization(Long chatId, UserEntity user) {
        if (user.getAuthorization()) {
            log.debug("Пользователь авторизован");
            return true;
        } else {
            log.warn("Пользователь user={} еще не авторизован", user);
            prepareAndSendMessage(chatId, "Пожалуйста введите пароль для авторизации: ");
            return false;
        }
    }

    private UserEntity registerUser(Message message) {
        var chatId = message.getChatId();

        UserEntity createdUser;

        if (userRepository.existsByChatId(chatId)) {
            createdUser = userRepository.findByChatId(chatId);
            log.info("Это наш старожил, пользователь уже есть в системе.");
            log.debug("Это наш старожил, пользователь уже есть в системе с tgId={}", chatId);
        } else {
            log.info("Начинаю регистрацию нового пользователя");
            log.debug("Начинаю регистрацию нового пользователя с tgId={}", chatId);

            var chat = message.getChat();
            UserEntity userForSave = UserEntity.builder()
                    .chatId(chatId)
                    .firstName(chat.getFirstName())
                    .lastName(chat.getLastName())
                    .userName(chat.getUserName())
                    .registeredOn(LocalDateTime.now())
                    .authorization(false)
                    .build();
            log.debug("Новый пользователь собран user={}", userForSave);

            createdUser = userRepository.save(userForSave);
            log.debug("Пользователь успешно сохранен в БД user={}", createdUser);
        }

        return createdUser;
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
