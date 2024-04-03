package com.github.jon7even.mapper;

import com.github.jon7even.dto.gift.GiftBuildingDto;
import com.github.jon7even.dto.gift.GiftShortDto;
import com.github.jon7even.model.gift.GiftEntity;
import com.github.jon7even.model.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GiftMapper {
    GiftMapper INSTANCE = Mappers.getMapper(GiftMapper.class);

    @Mapping(source = "giftEntity.name", target = "name")
    @Mapping(source = "giftEntity.status", target = "status")
    GiftShortDto toShortDtoFromEntity(GiftEntity giftEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "giftBuildingDto.name", target = "name")
    @Mapping(source = "userEntity", target = "creator")
    @Mapping(source = "giftBuildingDto.status", target = "status")
    GiftEntity toEntityFromBuildingDto(GiftBuildingDto giftBuildingDto, UserEntity userEntity);
}
