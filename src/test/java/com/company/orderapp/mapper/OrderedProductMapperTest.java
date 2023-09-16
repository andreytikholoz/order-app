package com.company.orderapp.mapper;

import com.company.orderapp.entity.OrderEntity;
import com.company.orderapp.entity.OrderedProductDTO;
import com.company.orderapp.entity.OrderedProductEntity;
import com.company.orderapp.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OrderedProductMapperTest {

    private final OrderedProductMapper orderedProductMapper = new OrderedProductMapper();

    @Test
    void map() {
        // Given
        List<OrderedProductEntity> orderedProductEntityList = new ArrayList<>();

        OrderedProductEntity orderedProductEntity1 = new OrderedProductEntity();

        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setId(UUID.randomUUID());
        productEntity1.setName("Iphone 10");
        productEntity1.setPrice(BigDecimal.valueOf(1000));
        productEntity1.setQuantity(10);

        orderedProductEntity1.setId(UUID.randomUUID());
        orderedProductEntity1.setOrder(new OrderEntity());
        orderedProductEntity1.setProduct(productEntity1);
        orderedProductEntity1.setQuantity(5);

        OrderedProductEntity orderedProductEntity2 = new OrderedProductEntity();

        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setId(UUID.randomUUID());
        productEntity2.setName("Iphone 11");
        productEntity2.setPrice(BigDecimal.valueOf(2000));
        productEntity2.setQuantity(5);

        orderedProductEntity2.setId(UUID.randomUUID());
        orderedProductEntity2.setOrder(new OrderEntity());
        orderedProductEntity2.setProduct(productEntity2);
        orderedProductEntity2.setQuantity(10);

        orderedProductEntityList.add(orderedProductEntity1);
        orderedProductEntityList.add(orderedProductEntity2);

        // When
        List<OrderedProductDTO> result = orderedProductMapper.map(orderedProductEntityList);

        // Then
        assertEquals(orderedProductEntityList.get(0).getProduct().getId(), result.get(0).getProductId());
        assertEquals(orderedProductEntityList.get(0).getQuantity(), result.get(0).getQuantity());

        assertEquals(orderedProductEntityList.get(1).getProduct().getId(), result.get(1).getProductId());
        assertEquals(orderedProductEntityList.get(1).getQuantity(), result.get(1).getQuantity());
    }
}
