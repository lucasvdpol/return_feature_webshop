package com.luxuryproductsholding.api.dao;

import com.luxuryproductsholding.api.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDAO {

    private final ProductRepository productRepository;

    public ProductDAO(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        return this.productRepository.save(product);
    }
}
