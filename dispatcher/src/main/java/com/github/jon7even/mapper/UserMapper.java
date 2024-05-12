package com.github.jon7even.mapper;

import com.github.jon7even.dto.UserShortDto;
import com.github.jon7even.entities.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.time.LocalDateTime;

/**
 * Интерфейс для маппинга DTO и сущностей пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "chatUser.id", target = "chatId")
    @Mapping(source = "chatUser.firstName", target = "firstName")
    @Mapping(source = "chatUser.lastName", target = "lastName")
    @Mapping(source = "chatUser.userName", target = "userName")
    @Mapping(source = "textMessage", target = "textMessage")
    UserShortDto toDtoFromMessage(Chat chatUser, String textMessage);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userShortDto.chatId", target = "chatId")
    @Mapping(source = "userShortDto.firstName", target = "firstName")
    @Mapping(source = "userShortDto.lastName", target = "lastName")
    @Mapping(source = "userShortDto.userName", target = "userName")
    @Mapping(source = "now", target = "registeredOn")
    @Mapping(constant = "false", target = "authorization")
    UserEntity toEntityFromShortDto(UserShortDto userShortDto, LocalDateTime now);
}
