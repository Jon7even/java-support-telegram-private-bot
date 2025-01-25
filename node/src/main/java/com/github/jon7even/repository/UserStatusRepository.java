package com.github.jon7even.repository;


import com.github.jon7even.entity.UserStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс DAO для статуса пользователя {@link UserStatusEntity}, использует JpaRepository.
 *
 * @author Jon7even
 * @version 2.0
 * @apiNote {@code chatId} присваивается самим Telegram и считается, что он уникальный.
 */
public interface UserStatusRepository extends JpaRepository<UserStatusEntity, Long> {
}