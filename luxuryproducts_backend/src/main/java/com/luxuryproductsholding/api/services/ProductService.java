package com.luxuryproductsholding.api.services;

import com.luxuryproductsholding.api.dao.ProductRepository;
import com.luxuryproductsholding.api.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAllHomePageProducts() {
        return productRepository.findAllHomePageProducts();
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new RuntimeException("Product not found with ID: " + id);
        }
    }

    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setProductBeschrijving(updatedProduct.getProductBeschrijving());
            existingProduct.setProductPrijs(updatedProduct.getProductPrijs());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setProductIMG(updatedProduct.getProductIMG());
            existingProduct.setProductQuantity(updatedProduct.getProductQuantity());
            existingProduct.setProductColor(updatedProduct.getProductColor());

            return productRepository.save(existingProduct);
        });
    }

}
