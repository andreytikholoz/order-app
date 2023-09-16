package com.company.orderapp.repository;

import com.company.orderapp.enums.OrderStatus;
import com.company.orderapp.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    List<OrderEntity> findByStatusAndCreatedDateBefore(OrderStatus status, LocalDateTime tenMinutesAgo);

}

