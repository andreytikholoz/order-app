package com.company.orderapp.controller;

import com.company.orderapp.api.ProductApi;
import com.company.orderapp.entity.ProductDTO;
import com.company.orderapp.entity.ProductListDTO;
import com.company.orderapp.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController implements ProductApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ProductDTO> addProduct(ProductDTO productDTO) {
        LOGGER.info("Received request to add product: {}", productDTO);
        ProductDTO addedProduct = productService.addProduct(productDTO);
        LOGGER.info("Product added successfully: {}", addedProduct);
        return ResponseEntity.ok(addedProduct);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_MANAGER')")
    public ResponseEntity<ProductListDTO> getProducts() {
        LOGGER.info("Received request to get products");
        ProductListDTO productList = productService.getProducts();
        LOGGER.info("Returning product list: {}", productList);
        return ResponseEntity.ok(productList);
    }
}
