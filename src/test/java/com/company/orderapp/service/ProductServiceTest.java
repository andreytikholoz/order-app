package com.company.orderapp.service;

import com.company.orderapp.entity.ProductDTO;
import com.company.orderapp.entity.ProductEntity;
import com.company.orderapp.entity.ProductListDTO;
import com.company.orderapp.mapper.ProductMapper;
import com.company.orderapp.repository.ProductRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductMapper productMapper = mock(ProductMapper.class);
    private final ProductService productService = new ProductService(productRepository, productMapper);

    @Test
    void addProduct() {
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(UUID.randomUUID());
        productDTO.setName("Iphone 10");
        productDTO.setPrice(BigDecimal.valueOf(10));
        productDTO.setQuantity(10);

        ProductEntity productEntity = new ProductEntity();

        when(productRepository.save(any())).thenReturn(productEntity);
        when(productMapper.map(any())).thenReturn(productDTO);

        // When
        ProductDTO response = productService.addProduct(productDTO);

        //Then
        verify(productRepository, times(1)).save(any());
        verify(productMapper, times(1)).map(any());
        Assertions.assertEquals(productDTO, response);
    }

    @Test
    void getProducts() {
        //Given
        List<ProductEntity> productEntities = Arrays.asList(
                new ProductEntity(UUID.randomUUID(), "Iphone 10", BigDecimal.valueOf(1000), 10),
                new ProductEntity(UUID.randomUUID(), "Iphone 11", BigDecimal.valueOf(2000), 5)
        );

        List<ProductDTO> expectedProducts = Arrays.asList(
                new ProductDTO(),
                new ProductDTO()
        );
        expectedProducts.get(0).setId(productEntities.get(0).getId());
        expectedProducts.get(0).setName("Iphone 10");
        expectedProducts.get(0).setPrice(BigDecimal.valueOf(1000));
        expectedProducts.get(0).setQuantity(10);
        expectedProducts.get(1).setId(productEntities.get(1).getId());
        expectedProducts.get(1).setName("Iphone 11");
        expectedProducts.get(1).setPrice(BigDecimal.valueOf(2000));
        expectedProducts.get(1).setQuantity(5);

        when(productRepository.findAll()).thenReturn(productEntities);
        when(productMapper.map(any())).thenAnswer(invocation -> {
            ProductEntity entity = invocation.getArgument(0);
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(entity.getId());
            productDTO.setName(entity.getName());
            productDTO.setPrice(entity.getPrice());
            productDTO.setQuantity(entity.getQuantity());
            return productDTO;
        });

        // When
        ProductListDTO productListDTO = productService.getProducts();

        // Then
        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(2)).map(any());
        Assertions.assertEquals(expectedProducts, productListDTO.getProducts());
    }

    @Test
    @SneakyThrows
    void shouldCreateEntity() {
        Method method = ProductService.class.getDeclaredMethod("createProductEntity", ProductDTO.class);
        method.setAccessible(true);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(UUID.randomUUID());
        productDTO.setName("Iphone 10");
        productDTO.setPrice(BigDecimal.valueOf(10));
        productDTO.setQuantity(10);

        ProductEntity result = (ProductEntity) method.invoke(productService, productDTO);
        Assertions.assertEquals(productDTO.getName(), result.getName());
        Assertions.assertEquals(productDTO.getPrice(), result.getPrice());
        Assertions.assertEquals(productDTO.getQuantity(), result.getQuantity());
    }
}