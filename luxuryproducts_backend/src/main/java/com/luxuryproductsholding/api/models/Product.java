package com.luxuryproductsholding.api.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productNummer;
    private String productName;
    private String productBeschrijving;
    private double productPrijs;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JsonManagedReference
    private Category category;
    private String productIMG;
    private long productQuantity;
    private long parentProductId;
    private String productColor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentProductId")
    private Set<Product> productVariants;

    public Product(long productNummer, String productName, String productBeschrijving, double productPrijs,
            Category productCategory, String productIMG, long productQuantity) {
        this.productNummer = productNummer;
        this.productName = productName;
        this.productBeschrijving = productBeschrijving;
        this.productPrijs = productPrijs;
        this.category = productCategory;
        this.productIMG = productIMG;
        this.productQuantity = productQuantity;
    }

    public Product(long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Product(String productName, String productBeschrijving, double productPrijs, Category productCategory,
            String productIMG, long productQuantity, String productColor) {
        this.productName = productName;
        this.productBeschrijving = productBeschrijving;
        this.productPrijs = productPrijs;
        this.category = productCategory;
        this.productIMG = productIMG;
        this.productQuantity = productQuantity;
        this.productColor = productColor;
    }

    public Product(String productName, String productBeschrijving, double productPrijs, Category productCategory,
            String productIMG, long productQuantity, String productColor, long parentProductId) {
        this.productName = productName;
        this.productBeschrijving = productBeschrijving;
        this.productPrijs = productPrijs;
        this.category = productCategory;
        this.productIMG = productIMG;
        this.productQuantity = productQuantity;
        this.productColor = productColor;
        this.parentProductId = parentProductId;
    }

    public Product() {

    }

    public long getProductNummer() {
        return productNummer;
    }

    public void setProductNummer(long productNummer) {
        this.productNummer = productNummer;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getProductIMG() {
        return productIMG;
    }

    public void setProductIMG(String productIMG) {
        this.productIMG = productIMG;
    }

    public long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Set<Product> getProductVariants() {
        return productVariants;
    }

    public void setProductVariants(Set<Product> productVariants) {
        this.productVariants = productVariants;
    }

    public long getParentProductId() {
        return parentProductId;
    }

    public void setParentProductId(long parentProductId) {
        this.parentProductId = parentProductId;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

}
