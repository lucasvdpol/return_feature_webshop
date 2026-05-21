package com.luxuryproductsholding.api.dao;

import com.luxuryproductsholding.api.models.Giftcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface GiftcardRepository extends JpaRepository<Giftcard, Long> {
    Optional<Giftcard> findById(Long id);
    List<Giftcard> findByUserEmail(String email);
    Optional<Giftcard> findByGiftcardCode(String giftcardCode);
    List<Giftcard> findByBlocked(boolean blocked);
    Optional<Giftcard> findByEndDate(Date endDate);
}
