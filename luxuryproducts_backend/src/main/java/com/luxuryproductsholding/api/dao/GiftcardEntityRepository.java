package com.luxuryproductsholding.api.dao;

import com.luxuryproductsholding.api.models.GiftcardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiftcardEntityRepository extends JpaRepository<GiftcardEntity, Long> {
    Optional<GiftcardEntity> findById(Long id);
}
