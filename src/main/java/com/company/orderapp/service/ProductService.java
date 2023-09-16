package com.company.orderapp.service;

import com.company.orderapp.entity.ProductDTO;
import com.company.orderapp.entity.ProductEntity;
import com.company.orderapp.entity.ProductListDTO;
import com.company.orderapp.mapper.ProductMapper;
import com.company.orderapp.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;

    @Transactional
    public ProductDTO addProduct(ProductDTO productDTO) {
        ProductEntity productEntity = createProductEntity(productDTO);
        ProductEntity createdProductEntity = productRepository.save(productEntity);
        return productMapper.map(createdProductEntity);
    }

    @Transactional(readOnly = true)
    public ProductListDTO getProducts() {
        List<ProductEntity> ProductEntities = productRepository.findAll();
        List<ProductDTO> products = ProductEntities
                .stream()
                .map(productMapper::map)
                .collect(Collectors.toList());
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
