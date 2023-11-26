package com.github.jon7even.cache;

public interface UserAuthCache {
    void setAttemptAuthForUserCache(Long userId, Integer attemptAuth);

    int getAttemptsAuthForUserCache(Long userId);

    void deleteUserFromAuthCache(Long userId);
}
