package com.company.orderapp.controller;

import com.company.orderapp.api.ProductApi;
import com.company.orderapp.entity.ProductDTO;
import com.company.orderapp.entity.ProductListDTO;
import com.company.orderapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController implements ProductApi {

    @Autowired
    ProductService productService;

    @Override
    public ResponseEntity<ProductDTO> addProduct(ProductDTO productDTO) {
        return ResponseEntity.ok(productService.addProduct(productDTO));
    }

    @Override
    public ResponseEntity<ProductListDTO> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }
}
