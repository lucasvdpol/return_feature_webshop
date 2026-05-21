package com.luxuryproductsholding.api.dto;

public class ReturnItemDTO {
    public String productName;
    public int quantity;
    public double price;
    public String reason;

    public ReturnItemDTO(String productName, int quantity, double price, String reason) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.reason = reason;
    }
}


