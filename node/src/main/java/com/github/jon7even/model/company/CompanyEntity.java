package com.github.jon7even.model.company;

import com.github.jon7even.model.user.UserEntity;
import com.github.jon7even.telegram.menu.gift.TypeGift;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
@Setter
@Getter
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "creator_id", nullable = false)
    @ToString.Exclude
    private UserEntity creator;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyEntity)) return false;
        CompanyEntity company = (CompanyEntity) o;
        return Objects.equals(id, company.id) && Objects.equals(nameCompany, company.nameCompany)
                && Objects.equals(totalSum, company.totalSum) && type == company.type
                && Objects.equals(created, company.created)
                && Objects.equals(updated, company.updated)
                && Objects.equals(isGiven, company.isGiven)
                && Objects.equals(given, company.given)
                && Objects.equals(creator, company.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameCompany, totalSum, type, created, updated, isGiven, given, creator);
    }

    @Override
    public String toString() {
        return nameCompany + " подарок=" + type.toString() + ", выдан ли подарок?=" + isGiven + "\n\n";
    }
}
