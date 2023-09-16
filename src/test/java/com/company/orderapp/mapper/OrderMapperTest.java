package com.company.orderapp.mapper;

import com.company.orderapp.entity.OrderDTO;
import com.company.orderapp.entity.OrderEntity;
import com.company.orderapp.enums.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderMapperTest {
    private final OrderedProductMapper orderedProductMapper = mock(OrderedProductMapper.class);
    private final OrderMapper orderMapper = new OrderMapper(orderedProductMapper);

    @Test
    void map() {
        // Given
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(UUID.randomUUID());
        orderEntity.setStatus(OrderStatus.UNPAID);
        orderEntity.setCreatedDate(LocalDateTime.now());
        orderEntity.setPrice(1000);

        when(orderedProductMapper.map(any())).thenReturn(new ArrayList<>());

        // When
        OrderDTO response = orderMapper.map(orderEntity, any());

        // Then
        verify(orderedProductMapper, times(1)).map(any());
        Assertions.assertEquals(orderEntity.getId(), response.getId());
        Assertions.assertEquals(orderEntity.getStatus(), response.getStatus());
        Assertions.assertEquals(orderEntity.getCreatedDate(), response.getCreatedDate());
        Assertions.assertEquals(orderEntity.getPrice(), response.getPrice());

    }
}