package com.company.orderapp.repository;

import com.company.orderapp.OrderStatus;
import com.company.orderapp.entity.OrderEntity;
import com.company.orderapp.entity.OrderedProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    List<OrderEntity> findByStatusAndCreatedDateBefore(OrderStatus status, LocalDateTime tenMinutesAgo);

//    @Query( "SELECT p FROM OrderEntity o Left Join fetch o.p")
//    List<OrderedProductEntity> getProductsByOrderId()
}

