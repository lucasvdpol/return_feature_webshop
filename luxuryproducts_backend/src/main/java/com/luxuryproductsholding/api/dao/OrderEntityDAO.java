package com.luxuryproductsholding.api.dao;

import com.luxuryproductsholding.api.models.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderEntityDAO {

    private final OrderEntityRepository orderEntityRepository;


    public OrderEntityDAO(OrderEntityRepository orderEntityRepository) {
        this.orderEntityRepository = orderEntityRepository;
    }

    public void addOrder(OrderEntity orderEntity){
       this.orderEntityRepository.save(orderEntity);

    }



}
