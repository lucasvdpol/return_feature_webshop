package com.luxuryproductsholding.api.dto;

import com.luxuryproductsholding.api.models.ProductOrder;

import java.util.Date;
import java.util.List;

public class OrderEntityDTO {
    public String email;
    public Date orderDate;
    public long totalPrice;
    public String address;
    public String postcode;
    public String city;
    public String country;
    public List<ProductOrderDTO> shoppingcart;
    public boolean requestedReturn;

}
