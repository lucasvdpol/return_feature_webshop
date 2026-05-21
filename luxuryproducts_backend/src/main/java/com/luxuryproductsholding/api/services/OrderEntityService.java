package com.luxuryproductsholding.api.services;

import com.luxuryproductsholding.api.dao.OrderEntityDAO;
import com.luxuryproductsholding.api.dao.OrderEntityRepository;
import com.luxuryproductsholding.api.models.OrderEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderEntityService {

    private final OrderEntityRepository orderEntityRepository;

    private final OrderEntityDAO orderEntityDAO;

    public OrderEntityService(OrderEntityRepository orderEntityRepository, OrderEntityDAO orderEntityDAO) {
        this.orderEntityRepository = orderEntityRepository;
        this.orderEntityDAO = orderEntityDAO;
    }


    public List<OrderEntity> getAllOrders() {
        return orderEntityRepository.findAll();
    }

    public List<OrderEntity> getOrdersByCustomerName(String customerName) {
        return orderEntityRepository.findByCustomerName(customerName);
    }

    public OrderEntity addOrder(OrderEntity orderEntity) {
        return orderEntityRepository.save(orderEntity);
    }
}
