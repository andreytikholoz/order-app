package com.company.orderapp.mapper;

import com.company.orderapp.entity.ProductEntity;
import com.company.orderapp.entity.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO map(ProductEntity goodsEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(goodsEntity.getId());
        productDTO.setName(goodsEntity.getName());
        productDTO.setPrice(goodsEntity.getPrice());
        productDTO.setQuantity(goodsEntity.getQuantity());
        return productDTO;
    }
}
