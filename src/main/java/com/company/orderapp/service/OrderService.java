package com.company.orderapp.service;

import com.company.orderapp.OrderStatus;
import com.company.orderapp.entity.*;
import com.company.orderapp.mapper.OrderMapper;
import com.company.orderapp.repository.OrderRepository;
import com.company.orderapp.repository.OrderedProductRepository;
import com.company.orderapp.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private OrderedProductRepository orderedProductRepository;
    private OrderMapper orderMapper;
    private EntityManager entityManager;

    public OrderDTO placeOrder(OrderDTO orderDTO) {
        checkIfOrderCanBePlaced(orderDTO);
        OrderEntity orderEntity = createOrderEntity(orderDTO);
        OrderEntity createdOrderEntity = orderRepository.save(orderEntity);
        List<OrderedProductEntity> createdOrderedProductEntityList = saveOrderedProducts(orderDTO, createdOrderEntity.getId());
        return orderMapper.map(createdOrderEntity, createdOrderedProductEntityList);
    }

    // mark orders as paid
    public void markOrderAsPaid(String orderId) {
        orderRepository.findById(UUID.fromString(orderId)).ifPresent(orderEntity -> {
            orderEntity.setStatus(OrderStatus.PAID);
            orderRepository.save(orderEntity);
            removePaidProducts(orderId);
        });
    }

    // delete orders that have not been paid for within 10 minutes
    public void deleteUnpaidOrders() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        List<OrderEntity> unpaidOrders = orderRepository.findByStatusAndCreatedDateBefore(OrderStatus.UNPAID, tenMinutesAgo);
        orderRepository.deleteAll(unpaidOrders);
    }

    private OrderEntity createOrderEntity(OrderDTO orderDTO) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(OrderStatus.UNPAID);
        orderEntity.setCreatedDate(LocalDateTime.now());
        orderEntity.setPrice(orderDTO.getPrice());
        return orderEntity;
    }

    private List<OrderedProductEntity> saveOrderedProducts(OrderDTO orderDTO, UUID orderId) {
        List<OrderedProductEntity> orderedProductEntityList = orderDTO.getProductList()
                .stream()
                .map(this::createOrderedProductEntity)
                .peek(entity -> entity.setOrder(entityManager.getReference(OrderEntity.class, orderId)))
                .collect(Collectors.toList());
        return orderedProductRepository.saveAll(orderedProductEntityList);
    }

    private OrderedProductEntity createOrderedProductEntity(OrderedProductDTO orderedProductDTO) {
        OrderedProductEntity orderedProductEntity = new OrderedProductEntity();
        orderedProductEntity.setProduct(entityManager.getReference(ProductEntity.class, orderedProductDTO.getProductId()));
        orderedProductEntity.setQuantity(orderedProductDTO.getQuantity());
        return orderedProductEntity;
    }

    //checks is it possible to place order by checking if products are in the database in the required quantity
    private void checkIfOrderCanBePlaced(OrderDTO orderDTO) {
        orderDTO.getProductList().forEach(productDTO -> {
            UUID orderedProductId = productDTO.getProductId();
            ProductEntity productEntity = productRepository.findById(orderedProductId)
                    .orElseThrow(() -> new NoSuchElementException("Product with id = [" + orderedProductId + "] not exist"));
            if (productEntity.getQuantity() < productDTO.getQuantity()) {
                throw new IllegalStateException("Products with id = [" + orderedProductId + "] less than you want to order");
            }
        });
    }

    //removes products from the ProductRepository that are in order and paid for
    private void removePaidProducts(String orderId) {
        UUID orderUUID = UUID.fromString(orderId);
        List<OrderedProductEntity> orderedProductEntityList = orderedProductRepository.findAllByOrderId(orderUUID);

        orderedProductEntityList.forEach(orderedProductEntity -> {
            UUID productId = orderedProductEntity.getProduct().getId();
            int quantityToRemove = orderedProductEntity.getQuantity();

            productRepository.findById(productId).ifPresent(productEntity -> {
                productEntity.setQuantity(productEntity.getQuantity() - quantityToRemove);
                productRepository.save(productEntity);
            });
        });
    }
}
