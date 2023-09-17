package com.company.orderapp.controller;


import com.company.orderapp.api.OrderApi;
import com.company.orderapp.entity.OrderDTO;
import com.company.orderapp.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements OrderApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    OrderService orderService;

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<OrderDTO> placeOrder(OrderDTO orderDTO) {
        LOGGER.info("Received request to place order: {}", orderDTO);
        OrderDTO placedOrder = orderService.placeOrder(orderDTO);
        LOGGER.info("Order placed successfully: {}", placedOrder);
        return ResponseEntity.ok(placedOrder);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> markOrderAsPaid(String id) {
        LOGGER.info("Received request to mark order as paid, ID: {}", id);
        orderService.markOrderAsPaid(id);
        LOGGER.info("Order marked as paid, ID: {}", id);
        return ResponseEntity.ok().build();
    }
}
