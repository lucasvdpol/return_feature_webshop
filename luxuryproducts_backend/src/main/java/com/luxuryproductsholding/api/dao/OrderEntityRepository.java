package com.luxuryproductsholding.api.dao;

import com.luxuryproductsholding.api.models.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCustomerName(String customerName);

}
