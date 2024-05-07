package com.github.jon7even.repository;

import com.github.jon7even.model.gift.GiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftRepository extends JpaRepository<GiftEntity, Long> {
}
