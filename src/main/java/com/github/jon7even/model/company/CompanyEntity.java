package com.github.jon7even.model.company;

import com.github.jon7even.model.user.UserEntity;
import com.github.jon7even.telegram.menu.gift.TypeGift;
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
@Table(name = "companies", schema = "public")
public class CompanyEntity {
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id", nullable = false)
    @ToString.Exclude
    private UserEntity creator;
}
