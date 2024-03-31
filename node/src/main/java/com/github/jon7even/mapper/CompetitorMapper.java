package com.github.jon7even.mapper;

import com.github.jon7even.dto.competitor.CompetitorBuildingDto;
import com.github.jon7even.dto.competitor.CompetitorShortDto;
import com.github.jon7even.model.competitor.CompetitorEntity;
import com.github.jon7even.model.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface CompetitorMapper {
    CompetitorMapper INSTANCE = Mappers.getMapper(CompetitorMapper.class);

    @Mapping(source = "competitorEntity.name", target = "name")
    CompetitorShortDto toDtoFromEntity(CompetitorEntity competitorEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "competitorBuildingDto.name", target = "name")
    @Mapping(source = "now", target = "created")
    @Mapping(source = "userEntity", target = "creator")
    CompetitorEntity toEntityFromShortDto(CompetitorBuildingDto competitorBuildingDto,
                                          LocalDateTime now,
                                          UserEntity userEntity);
}
