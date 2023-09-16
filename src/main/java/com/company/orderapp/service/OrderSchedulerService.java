package com.company.orderapp.service;

import com.company.orderapp.entity.OrderEntity;
import com.company.orderapp.enums.OrderStatus;
import com.company.orderapp.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderSchedulerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSchedulerService.class);

    private OrderRepository orderRepository;

    public void deleteUnpaidOrders() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);

        LOGGER.info("Starting to delete unpaid orders created before: {}", tenMinutesAgo);

        List<OrderEntity> unpaidOrders = orderRepository.findByStatusAndCreatedDateBefore(OrderStatus.UNPAID, tenMinutesAgo);

        LOGGER.info("Found {} unpaid orders to delete.", unpaidOrders.size());

        orderRepository.deleteAll(unpaidOrders);

        LOGGER.info("Deleted {} unpaid orders.", unpaidOrders.size());
    }
}
