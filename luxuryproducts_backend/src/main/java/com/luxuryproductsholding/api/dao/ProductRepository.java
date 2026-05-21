package com.luxuryproductsholding.api.dao;

import com.luxuryproductsholding.api.models.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT r FROM Product r WHERE r.parentProductId = 0")
    List<Product> findAllHomePageProducts();

    Product findByProductName(String name);

}
