package com.luxuryproductsholding.api.dto;

public class GiftcardEntityDTO {

    public String img;
    public double price;
    public String description;

    public GiftcardEntityDTO(String img, double price, String description) {
        this.img = img;
        this.price = price;
        this.description = description;
    }
}
