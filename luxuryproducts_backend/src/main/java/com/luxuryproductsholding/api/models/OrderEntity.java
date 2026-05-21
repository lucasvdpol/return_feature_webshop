package com.luxuryproductsholding.api.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String customerName;
    private Date orderDate;
    private double totalPrice;
    private String address;
    private String postcode;
    private String city;
    private String country;
    private boolean requestedReturn;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ProductOrder> winkelwagen;

    public OrderEntity(String customerName, Date orderDate, double totalPrice, String address, String postcode, String city, String country, boolean requestedReturn) {
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
        this.country = country;
        this.requestedReturn = requestedReturn;
    }

    public OrderEntity(String customerName, Date orderDate, double totalPrice, List<ProductOrder> winkelwagen) {
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.winkelwagen = winkelwagen;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderEntity() {
    }

    public List<ProductOrder> getWinkelwagen() {
        return winkelwagen;
    }

    public void setWinkelwagen(List<ProductOrder> winkelwagen) {
        this.winkelwagen = winkelwagen;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isRequestedReturn() {
        return requestedReturn;
    }

    public void setRequestedReturn(boolean requestedReturn) {
        this.requestedReturn = requestedReturn;
    }
}
