package com.company.orderapp.mapper;

import com.company.orderapp.entity.ProductDTO;
import com.company.orderapp.entity.ProductEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
class ProductMapperTest {
    private final ProductMapper productMapper = new ProductMapper();

    @Test
    void map() {
        // Given
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(UUID.randomUUID());
        productEntity.setName("Iphone 10");
        productEntity.setPrice(BigDecimal.valueOf(1000));
        productEntity.setQuantity(5);

        //  When
        ProductDTO response = productMapper.map(productEntity);

        // Then
        Assertions.assertEquals(productEntity.getId(), response.getId());
        Assertions.assertEquals(productEntity.getName(), response.getName());
        Assertions.assertEquals(productEntity.getPrice(), response.getPrice());
        Assertions.assertEquals(productEntity.getQuantity(), response.getQuantity());
    }
}