package com.github.jon7even.repository;

import com.github.jon7even.entity.company.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс DAO для компаний(CompanyEntity), использует JpaRepository
 *
 * @author Jon7even
 * @version 1.0
 */
@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
}
