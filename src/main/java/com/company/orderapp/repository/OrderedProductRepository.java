package com.company.orderapp.repository;

import com.company.orderapp.entity.OrderedProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProductEntity, UUID> {
    List<OrderedProductEntity> findAllByOrderId(UUID orderId);
}
