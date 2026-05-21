package com.luxuryproductsholding.api.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Random;

@Entity
public class Giftcard {
    @Id
    @GeneratedValue
    private long id;

    private String giftcardCode;


    private Date date;
    private Date endDate;
    private double price;

    @Column(columnDefinition = "TEXT")
    private String geschiedenis;
    private Boolean blocked;

    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private CustomUser user;

    public Giftcard() {}

    public Giftcard(Date date, double price, String geschiedenis, Boolean blocked, CustomUser user, Date endDate) {
        this.date = date;
        this.price = price;
        this.giftcardCode = generateGiftcardCode();
        this.geschiedenis = geschiedenis;
        this.blocked = blocked;
        this.user = user;
        this.endDate = endDate;
    }

    public Giftcard(Date date, double price, String geschiedenis, Boolean blocked, Date endDate) {
        this.giftcardCode = generateGiftcardCode();
        this.date = date;
        this.price = price;
        this.geschiedenis = geschiedenis;
        this.blocked = blocked;
        this.user = null;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGiftcardCode() {
        return giftcardCode;
    }

    public void setGiftcardCode(String giftcardCode) {
        this.giftcardCode = giftcardCode;
    }

    public String getGeschiedenis() {
        return geschiedenis;
    }

    public void setGeschiedenis(String geschiedenis) {
        this.geschiedenis = geschiedenis;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    private static String generateGiftcardCode() {
        StringBuilder code = new StringBuilder("LP");
        Random random = new Random();

        for (int i = 0; i < 14; i++) {
            code.append(random.nextInt(10));
        }

        return code.toString();
    }

}
