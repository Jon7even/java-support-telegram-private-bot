package com.github.jon7even.model.gift;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gifts", schema = "public")
public class GiftEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_company", nullable = false)
    private String nameCompany;

    @Column(name = "total_sum", nullable = false)
    private Integer totalSum;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gift")
    private TypeGift type;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime created;

    @Column(name = "updated_on")
    private LocalDateTime updated;

    @Column(name = "is_given", nullable = false)
    private Boolean isGiven;

    @Column(name = "given_on")
    private LocalDateTime given;
}
