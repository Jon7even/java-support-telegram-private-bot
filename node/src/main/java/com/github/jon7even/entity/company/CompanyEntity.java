package com.github.jon7even.entity.company;

import com.github.jon7even.entity.user.UserEntity;
import com.github.jon7even.entity.gift.GiftEntity;
import jakarta.persistence.*;
import lombok.*;

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
    private String name;

    @Column(name = "total_sum", nullable = false)
    private Long totalSum;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "gift_id", nullable = false)
    @ToString.Exclude
    private GiftEntity gift;

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
        if (!(o instanceof CompanyEntity company)) return false;
        return Objects.equals(id, company.id) && Objects.equals(name, company.name)
                && Objects.equals(totalSum, company.totalSum) && gift == company.gift
                && Objects.equals(created, company.created)
                && Objects.equals(updated, company.updated)
                && Objects.equals(isGiven, company.isGiven)
                && Objects.equals(given, company.given)
                && Objects.equals(creator, company.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, totalSum, gift, created, updated, isGiven, given, creator);
    }

    @Override
    public String toString() {
        return name + " подарок=" + gift.toString() + ", выдан ли подарок?=" + isGiven + "\n\n";
    }
}