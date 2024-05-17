package com.github.jon7even.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.github.jon7even.constants.DataTimePattern.DATE_TIME_FORMATTER;

/**
 * Класс описывающий сущность пользователя
 *
 * @author Jon7even
 * @version 1.0
 */
@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "bot")
public class UserEntity {
    @Id
    @SequenceGenerator(name = "UserGenId", sequenceName = "user_seq", allocationSize = 7)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserGenId")
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "register_on", nullable = false)
    private LocalDateTime registeredOn;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "auth_on")
    @ColumnDefault(value = "false")
    private Boolean authorization;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return id.equals(that.id)
                && chatId.equals(that.chatId)
                && firstName.equals(that.firstName)
                && lastName.equals(that.lastName)
                && userName.equals(that.userName)
                && registeredOn.equals(that.registeredOn)
                && updatedOn.equals(that.updatedOn)
                && authorization.equals(that.authorization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, firstName, lastName, userName, registeredOn, updatedOn, authorization);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", registeredOn=" + registeredOn.format(DATE_TIME_FORMATTER) + '\'' +
                ", authorization='" + authorization +
                '}';
    }
}
