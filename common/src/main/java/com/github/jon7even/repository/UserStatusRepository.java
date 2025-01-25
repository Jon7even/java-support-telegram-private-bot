package com.github.jon7even.repository;

import com.github.jon7even.entity.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс DAO для статуса пользователя {@link UserStatus}, использует JpaRepository.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote chatId присваивается самим Telegram и считается, что он уникальный
 */
public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
}