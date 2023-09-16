package com.company.orderapp.schedulers;

import com.company.orderapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class OrderScheduler {
    @Autowired
    private OrderService orderService;

    @Scheduled(fixedDelay = 120000)
    public void deleteUnpaidOrders() {
        orderService.deleteUnpaidOrders();
    }
}
