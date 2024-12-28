package com.github.jon7even.mapper;

import com.github.jon7even.dto.UserAuthFalseDto;
import com.github.jon7even.dto.UserAuthTrueDto;
import com.github.jon7even.dto.UserCreateDto;
import com.github.jon7even.dto.UserUpdateDto;
import com.github.jon7even.entity.user.UserEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

import java.time.LocalDateTime;

/**
 * Интерфейс для маппинга DTO и сущностей пользователя
 *
 * @author Jon7even
 * @version 2.0
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "chatUser.id", target = "chatId")
    @Mapping(source = "chatUser.firstName", target = "firstName")
    @Mapping(source = "chatUser.lastName", target = "lastName")
    @Mapping(source = "chatUser.userName", target = "userName")
    UserCreateDto toDtoCreateFromMessage(Chat chatUser);

    @Mapping(source = "chatUser.id", target = "chatId")
    @Mapping(source = "chatUser.firstName", target = "firstName")
    @Mapping(source = "chatUser.lastName", target = "lastName")
    @Mapping(source = "chatUser.userName", target = "userName")
    UserUpdateDto toDtoUpdateFromMessage(Chat chatUser);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userCreateDto.chatId", target = "chatId")
    @Mapping(source = "userCreateDto.firstName", target = "firstName")
    @Mapping(source = "userCreateDto.lastName", target = "lastName")
    @Mapping(source = "userCreateDto.userName", target = "userName")
    @Mapping(source = "now", target = "registeredOn")
    @Mapping(constant = "false", target = "authorization")
    @Mapping(target = "updatedOn", ignore = true)
    UserEntity toEntityFromCreateDto(UserCreateDto userCreateDto, LocalDateTime now);

    @Mapping(source = "userEntity.id", target = "id")
    @Mapping(source = "userEntity.chatId", target = "chatId")
    @Mapping(source = "userEntity.firstName", target = "firstName")
    @Mapping(source = "userEntity.lastName", target = "lastName")
    @Mapping(source = "userEntity.userName", target = "userName")
    @Mapping(source = "userEntity.registeredOn", target = "registeredOn")
    @Mapping(constant = "0", target = "attemptAuth")
    UserAuthFalseDto toAuthFalseDtoFromEntity(UserEntity userEntity);

    @Mapping(source = "userEntity.id", target = "id")
    @Mapping(source = "userEntity.chatId", target = "chatId")
    @Mapping(source = "userEntity.firstName", target = "firstName")
    @Mapping(source = "userEntity.lastName", target = "lastName")
    @Mapping(source = "userEntity.userName", target = "userName")
    @Mapping(source = "userEntity.registeredOn", target = "registeredOn")
    UserAuthTrueDto toAuthTrueDtoFromEntity(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "chatId", ignore = true)
    @Mapping(source = "userUpdateDto.firstName", target = "firstName")
    @Mapping(source = "userUpdateDto.lastName", target = "lastName")
    @Mapping(source = "userUpdateDto.userName", target = "userName")
    @Mapping(target = "registeredOn", ignore = true)
    @Mapping(source = "now", target = "updatedOn")
    @Mapping(source = "isAuthorization", target = "authorization")
    void updateUserEntityFromDtoUpdate(@MappingTarget UserEntity userEntity,
                                       UserUpdateDto userUpdateDto,
                                       LocalDateTime now,
                                       boolean isAuthorization);
}