package com.github.jon7even.mapper;

import com.github.jon7even.entity.UserStatusEntity;
import com.github.jon7even.telegram.BotState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Интерфейс для маппинга DTO и сущностей статуса бота для пользователя {@link UserStatusEntity}.
 *
 * @author Jon7even
 * @version 2.0
 */
@Mapper(componentModel = "spring")
public interface UserStatusMapper {

    @Mapping(source = "chatId", target = "chatId")
    @Mapping(source = "status", target = "status")
    UserStatusEntity toUserStatusEntityFromChatIdAndStatus(Long chatId, BotState status);

    @Mapping(source = "chatId", target = "chatId")
    @Mapping(constant = "MAIN_START", target = "status")
    UserStatusEntity toDefaultUserStatusEntityFromChatId(Long chatId);
}