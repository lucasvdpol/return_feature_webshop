package com.luxuryproductsholding.api.controllers;

import com.luxuryproductsholding.api.dao.GiftcardEntityDAO;
import com.luxuryproductsholding.api.models.GiftcardEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/giftcardEntity")
public class GiftcardEntityController {

    private final GiftcardEntityDAO giftcardEntityDAO;

    public GiftcardEntityController(GiftcardEntityDAO giftcardEntityDAO) {
        this.giftcardEntityDAO = giftcardEntityDAO;
    }

    @GetMapping
    public ResponseEntity<List<GiftcardEntity>> getAll() {
        return ResponseEntity.ok(this.giftcardEntityDAO.getAllGiftcardEntity());
    }
}
