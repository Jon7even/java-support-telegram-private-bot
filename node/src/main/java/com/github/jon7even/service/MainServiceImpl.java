package com.github.jon7even.service;

import com.github.jon7even.cache.UserDataCache;
import com.github.jon7even.model.company.CompanyEntity;
import com.github.jon7even.repository.CompanyRepository;
import com.github.jon7even.repository.UserRepository;
import com.github.jon7even.service.message.ReplyMessageService;
import com.github.jon7even.service.producer.SenderMessageService;
import com.github.jon7even.telegram.BotState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.github.jon7even.telegram.menu.gift.MenuGift.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UserDataCache userDataCache;
    private final ReplyMessageService replyMessageService;
    private final SenderMessageService senderMessageService;

    @Override
    public void processTextMessage(Update update) {
        //TODO
        String resultMessage = update.getMessage().getText();
        Long chaId = update.getMessage().getChatId();

        switch (resultMessage) {
            case "/gifts":
                giftsCommandReceived(chaId);
                break;
            default:
                senderMessageService.sendText(chaId, replyMessageService.getReplyText("reply.nonSupport"));
                userDataCache.setBotStateForCacheUser(chaId, BotState.MAIN_HELP);
                log.warn("Эту команду мы еще не поддерживаем. Команда пользователя: " + resultMessage);
        }
    }

    @Override
    public void processCallbackQuery(Update update) {
        //TODO
        String result = update.getCallbackQuery().getData();
        Long chaId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();

        switch (result) {
            case "/newcompany":
                senderMessageService.sendEditText(
                        chaId, replyMessageService.getReplyText("reply.callBackAddNewCompany"), messageId
                );
                userDataCache.setBotStateForCacheUser(chaId, BotState.COMPANY_NAME);
                break;
            case "/givegift":
                senderMessageService.sendEditText(chaId, "TODO", messageId);
                break;
            case "/givemanual":
                senderMessageService.sendEditText(chaId, "TODO", messageId);
                break;
            case "/searchcompany":
                senderMessageService.sendEditText(chaId, "TODO", messageId);
                break;
            case "/removecompany":
                senderMessageService.sendEditText(chaId, "TODO", messageId);
                break;
            case "/checkgifts":
                senderMessageService.sendEditText(chaId, "Общий список компаний: ", messageId);
                processGetListCompanies(chaId);
                break;
            default:
                senderMessageService.sendText(chaId, replyMessageService.getReplyText("reply.nonSupport"));
                log.warn("Эту команду мы еще не поддерживаем. Команда пользователя: " + result);
        }
    }

    private void processGetListCompanies(Long chaId) {
        List<CompanyEntity> listCompanies = companyRepository.findAll();
        log.debug("Список компаний: {}", listCompanies);
        String answer = StringUtils.join(listCompanies);
        senderMessageService.sendText(chaId, answer);
    }

    private void giftsCommandReceived(Long chatId) {
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

        senderMessageService.sendTextAndMarkup(chatId, "/TODO", markup);
    }

}
