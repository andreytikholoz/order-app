package com.company.orderapp.mapper;

import com.company.orderapp.entity.OrderedProductDTO;
import com.company.orderapp.entity.OrderedProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderedProductMapper {
    public List<OrderedProductDTO> map(List<OrderedProductEntity> orderedProductEntityList) {
        return orderedProductEntityList.stream()
                .map(orderedProductEntity -> {
                    OrderedProductDTO orderedProductDTO = new OrderedProductDTO();
                    orderedProductDTO.setProductId(orderedProductEntity.getProduct().getId());
                    orderedProductDTO.setQuantity(orderedProductEntity.getQuantity());
                    return orderedProductDTO;
                })
                .collect(Collectors.toList());
    }
}
