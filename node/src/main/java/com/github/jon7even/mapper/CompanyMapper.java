package com.github.jon7even.mapper;

import com.github.jon7even.dto.company.CompanyBuildingDto;
import com.github.jon7even.dto.company.CompanyShortDto;
import com.github.jon7even.model.company.CompanyEntity;
import com.github.jon7even.model.gift.GiftEntity;
import com.github.jon7even.model.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "companyBuildingDto.name", target = "name")
    @Mapping(source = "companyBuildingDto.totalSum", target = "totalSum")
    @Mapping(source = "gift", target = "gift")
    @Mapping(source = "now", target = "created")
    @Mapping(source = "userEntity", target = "creator")
    @Mapping(source = "companyBuildingDto.isGiven", target = "isGiven")
    CompanyEntity toEntityFromBuildingDto(CompanyBuildingDto companyBuildingDto,
                                          UserEntity userEntity,
                                          GiftEntity gift,
                                          LocalDateTime now);
}
