package com.luxuryproductsholding.api.dto;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class ProductDTO {

    private String productName;
    private String productBeschrijving;
    private double productPrijs;
    private String productCategory;

    public ProductDTO(String productName, String productBeschrijving, double productPrijs, String productCategory) {
        this.productName = productName;
        this.productBeschrijving = productBeschrijving;
        this.productPrijs = productPrijs;
        this.productCategory = productCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBeschrijving() {
        return productBeschrijving;
    }

    public void setProductBeschrijving(String productBeschrijving) {
        this.productBeschrijving = productBeschrijving;
    }

    public double getProductPrijs() {
        return productPrijs;
    }

    public void setProductPrijs(double productPrijs) {
        this.productPrijs = productPrijs;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
}
