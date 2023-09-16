package com.company.orderapp.service;

import com.company.orderapp.entity.OrderDTO;
import com.company.orderapp.entity.OrderEntity;
import com.company.orderapp.entity.OrderedProductEntity;
import com.company.orderapp.entity.ProductEntity;
import com.company.orderapp.enums.OrderStatus;
import com.company.orderapp.mapper.OrderMapper;
import com.company.orderapp.repository.OrderRepository;
import com.company.orderapp.repository.OrderedProductRepository;
import com.company.orderapp.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceTest {

    private final OrderRepository orderRepository = mock(OrderRepository.class);
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final OrderedProductRepository orderedProductRepository = mock(OrderedProductRepository.class);
    private final OrderMapper orderMapper = mock(OrderMapper.class);
    private final EntityManager entityManager = mock(EntityManager.class);

    private final OrderService orderService = new OrderService(orderRepository, productRepository, orderedProductRepository, orderMapper, entityManager);

    @Test
    void placeOrder() {
        // Given
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(UUID.randomUUID());
        orderDTO.setStatus(OrderStatus.UNPAID);
        orderDTO.setCreatedDate(LocalDateTime.now());
        orderDTO.setPrice(1000.0);


        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(UUID.randomUUID());
        orderEntity.setStatus(OrderStatus.UNPAID);
        orderEntity.setCreatedDate(LocalDateTime.now());
        orderEntity.setPrice(1000.0);

        List<OrderedProductEntity> orderedProductEntityList = new ArrayList<>();

        OrderedProductEntity orderedProductEntity = new OrderedProductEntity();
        orderedProductEntity.setId(UUID.randomUUID());
        orderedProductEntity.setQuantity(5);
        orderedProductEntity.setProduct(new ProductEntity());
        orderedProductEntityList.add(orderedProductEntity);

        when(orderRepository.save(any())).thenReturn(orderEntity);
        when(orderedProductRepository.saveAll(any())).thenReturn(orderedProductEntityList);
        when(orderMapper.map(any(), any())).thenReturn(new OrderDTO());

        // When
        OrderDTO response = orderService.placeOrder(orderDTO);

        // Then
        verify(orderRepository, times(1)).save(any());
        verify(orderedProductRepository, times(1)).saveAll(any());
        verify(orderMapper, times(1)).map(any(), any());
        Assertions.assertNotNull(response);
    }

    @Test
    void markOrderAsPaid() {

        // Given
        UUID orderId = UUID.randomUUID();

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderId);
        orderEntity.setStatus(OrderStatus.UNPAID);
        orderEntity.setCreatedDate(LocalDateTime.now());
        orderEntity.setPrice(1000.0);

        List<OrderedProductEntity> orderedProductEntityList = new ArrayList<>();

        OrderedProductEntity orderedProductEntity = new OrderedProductEntity();
        orderedProductEntity.setId(UUID.randomUUID());
        orderedProductEntity.setQuantity(5);
        orderedProductEntity.setProduct(new ProductEntity());
        orderedProductEntityList.add(orderedProductEntity);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(UUID.randomUUID());
        productEntity.setName("Iphone 10");
        productEntity.setPrice(BigDecimal.valueOf(1000));
        productEntity.setQuantity(10);

        when(orderedProductRepository.findAllByOrderId(any())).thenReturn(orderedProductEntityList);
        when(orderRepository.findById(any())).thenReturn(Optional.of(orderEntity));
        when(productRepository.findById(any())).thenReturn(Optional.of(productEntity));

        // When
        orderService.markOrderAsPaid(String.valueOf(orderId));

        // Then
        verify(orderedProductRepository, times(2)).findAllByOrderId(any());
        verify(productRepository, times(2)).findById(any());
        verify(orderRepository, times(1)).findById(any());
        verify(orderRepository, times(1)).save(any());
    }
}