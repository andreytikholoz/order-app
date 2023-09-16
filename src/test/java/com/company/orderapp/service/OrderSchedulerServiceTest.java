package com.company.orderapp.service;

import com.company.orderapp.entity.OrderEntity;
import com.company.orderapp.repository.OrderRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderSchedulerServiceTest {
    private final OrderRepository orderRepository = mock(OrderRepository.class);
    private final OrderSchedulerService orderSchedulerService = new OrderSchedulerService(orderRepository);

    @Test
    void deleteUnpaidOrders() {
        // Given
        List<OrderEntity> unpaidOrders = new ArrayList<>();

        when(orderRepository.findByStatusAndCreatedDateBefore(any(), any())).thenReturn(unpaidOrders);

        // When
        orderSchedulerService.deleteUnpaidOrders();

        // Then
        verify(orderRepository, times(1)).findByStatusAndCreatedDateBefore(any(), any());
        verify(orderRepository, times(1)).deleteAll(unpaidOrders);
    }
}