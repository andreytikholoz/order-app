package com.company.orderapp.mapper;

import com.company.orderapp.entity.OrderDTO;
import com.company.orderapp.entity.OrderEntity;
import com.company.orderapp.entity.OrderedProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class OrderMapper {

    private OrderedProductMapper orderedProductMapper;

    public OrderDTO map(OrderEntity orderEntity, List<OrderedProductEntity> orderedProductEntityList) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderEntity.getId());
        orderDTO.setProductList(orderedProductMapper.map(orderedProductEntityList));
        orderDTO.setStatus(orderEntity.getStatus());
        orderDTO.setCreatedDate(orderEntity.getCreatedDate());
        orderDTO.setPrice(orderEntity.getPrice());
        return orderDTO;
    }
}
