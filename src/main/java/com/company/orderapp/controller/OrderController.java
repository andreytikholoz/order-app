package com.company.orderapp.controller;


import com.company.orderapp.api.OrderApi;
import com.company.orderapp.entity.OrderDTO;
import com.company.orderapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements OrderApi {

    @Autowired
    OrderService orderService;

    @Override
    public ResponseEntity<OrderDTO> placeOrder(OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.placeOrder(orderDTO));
    }

    @Override
    public ResponseEntity<Void> markOrderAsPaid(String id) {
        orderService.markOrderAsPaid(id);
        return ResponseEntity.ok().build();
    }

}
