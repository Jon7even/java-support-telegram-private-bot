package com.github.jon7even.cache;

import com.github.jon7even.dto.company.CompanyBuildingDto;
import com.github.jon7even.telegram.BotState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class UserDataCacheImpl implements UserDataCache {
    private final HashMap<Long, BotState> botStatesOfUsers;
    private final HashMap<Long, CompanyBuildingDto> companiesOfUsers;

    @Override
    public void setBotStateForCacheUser(Long userId, BotState botState) {
        botStatesOfUsers.put(userId, botState);
    }

    @Override
    public void setCompanyForCacheUser(Long userId, CompanyBuildingDto companyBuildingDto) {
        companiesOfUsers.put(userId, companyBuildingDto);
    }

    @Override
    public BotState getBotStateFromCache(Long userId) {
        BotState state = botStatesOfUsers.get(userId);

        if (state == null) {
            state = BotState.MAIN_HELP;
        }

        return state;
    }

    @Override
    public CompanyBuildingDto getCompanyFromCache(Long userId) {
        CompanyBuildingDto companyBuildingDto = companiesOfUsers.get(userId);

        if (companyBuildingDto == null) {
            companyBuildingDto = CompanyBuildingDto.builder().build();
        }

        return companyBuildingDto;
    }
}
