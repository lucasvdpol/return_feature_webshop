package com.luxuryproductsholding.api.dto;


import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Date;

public class GiftcardDTO {

    public Date date;
    public Date endDate;
    public double price;
    public String geschiedenis;
    public Boolean blocked;

    @JsonAlias("customUser_email")
    public String userEmail;

    public GiftcardDTO() {

    }

    public GiftcardDTO(Date date, double price, String geschiedenis, Boolean blocked, String userEmail, Date endDate) {
        this.date = date;
        this.price = price;
        this.geschiedenis = geschiedenis;
        this.blocked = blocked;
        this.userEmail = userEmail;
        this.endDate = endDate;
    }
}
