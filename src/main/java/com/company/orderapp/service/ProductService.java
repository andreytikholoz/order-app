package com.company.orderapp.service;

import com.company.orderapp.entity.ProductDTO;
import com.company.orderapp.entity.ProductEntity;
import com.company.orderapp.entity.ProductListDTO;
import com.company.orderapp.mapper.ProductMapper;
import com.company.orderapp.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;
    private ProductMapper productMapper;

    @Transactional
    public ProductDTO addProduct(ProductDTO productDTO) {
        LOGGER.info("Adding a new product: {}", productDTO);

        ProductEntity productEntity = createProductEntity(productDTO);
        ProductEntity createdProductEntity = productRepository.save(productEntity);

        LOGGER.info("Product added successfully. Product ID: {}", createdProductEntity.getId());

        return productMapper.map(createdProductEntity);
    }

    @Transactional(readOnly = true)
    public ProductListDTO getProducts() {
        LOGGER.info("Getting the list of products");

        List<ProductEntity> productEntities = productRepository.findAll();
        List<ProductDTO> products = productEntities
                .stream()
                .map(productMapper::map)
                .collect(Collectors.toList());

        LOGGER.info("Retrieved {} products", products.size());

        ProductListDTO productListDTO = new ProductListDTO();
        productListDTO.setProducts(products);
        return productListDTO;
    }

    private ProductEntity createProductEntity(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDTO.getName());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setQuantity(productDTO.getQuantity());
        return productEntity;
    }
}

