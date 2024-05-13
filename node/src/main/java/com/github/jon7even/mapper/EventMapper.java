package com.github.jon7even.mapper;

import com.github.jon7even.dto.event.EventBuildingDto;
import com.github.jon7even.dto.event.EventShortDto;
import com.github.jon7even.entity.user.UserEntity;
import com.github.jon7even.entity.event.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(source = "eventEntity.name", target = "name")
    @Mapping(source = "eventEntity.start", target = "start")
    @Mapping(source = "eventEntity.end", target = "end")
    @Mapping(source = "eventEntity.status", target = "status")
    EventShortDto toShortDtoFromEntity(EventEntity eventEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "eventBuildingDto.name", target = "name")
    @Mapping(source = "eventBuildingDto.start", target = "start")
    @Mapping(source = "eventBuildingDto.end", target = "end")
    @Mapping(source = "eventBuildingDto.status", target = "status")
    @Mapping(source = "userEntity", target = "creator")
    EventEntity toEntityFromBuildingDto(EventBuildingDto eventBuildingDto, UserEntity userEntity);
}
