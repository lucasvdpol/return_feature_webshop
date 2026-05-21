package com.luxuryproductsholding.api.dao;

import com.luxuryproductsholding.api.models.GiftcardEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GiftcardEntityDAO {

    private final GiftcardEntityRepository giftcardEntityRepository;

    public GiftcardEntityDAO(GiftcardEntityRepository giftcardEntityRepository) {
        this.giftcardEntityRepository = giftcardEntityRepository;
    }

    public List<GiftcardEntity> getAllGiftcardEntity() {
        return this.giftcardEntityRepository.findAll();
    }
}
