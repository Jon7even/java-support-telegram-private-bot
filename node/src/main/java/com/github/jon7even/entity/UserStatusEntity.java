package com.github.jon7even.entity;

import com.github.jon7even.telegram.BotState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Класс описывающий статус пользователя.
 *
 * @author Jon7even
 * @version 2.0
 */
@Data
@Entity
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "user_status", schema = "bot")
public class UserStatusEntity {

    @Id
    @NonNull
    @EqualsAndHashCode.Include
    @Column(name = "chat_id", nullable = false)
    private final Long chatId;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private final BotState status;
}