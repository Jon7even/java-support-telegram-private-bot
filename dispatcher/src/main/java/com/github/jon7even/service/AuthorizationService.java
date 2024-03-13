package com.github.jon7even.service;

import com.github.jon7even.dto.UserShortDto;

public interface AuthorizationService {
    boolean processAuthorization(UserShortDto userShortDto);

    boolean processAuthorizationForCallBack(Long userId);
}
