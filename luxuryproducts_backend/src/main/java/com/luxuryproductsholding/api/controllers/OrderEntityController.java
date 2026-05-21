package com.luxuryproductsholding.api.controllers;

import com.luxuryproductsholding.api.models.OrderEntity;
import com.luxuryproductsholding.api.services.OrderEntityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderEntity")
public class OrderEntityController {

    private final OrderEntityService orderEntityService;


    public OrderEntityController(OrderEntityService orderEntityService) {
        this.orderEntityService = orderEntityService;
    }

    @GetMapping
    public List<OrderEntity> getOrders() {
        return orderEntityService.getAllOrders();
    }

    @GetMapping("/{customerName}")
    public List<OrderEntity> getOrdersByCustomerName(@PathVariable String customerName) {
        return orderEntityService.getOrdersByCustomerName(customerName);
    }

    @PostMapping
    public OrderEntity createOrder(@RequestBody OrderEntity orderEntity) {
        return orderEntityService.addOrder(orderEntity);
    }
}
