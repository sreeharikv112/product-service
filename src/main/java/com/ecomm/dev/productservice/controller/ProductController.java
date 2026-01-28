package com.ecomm.dev.productservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecomm.dev.productservice.dto.ProductRequest;
import com.ecomm.dev.productservice.dto.ProductResponse;
import com.ecomm.dev.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("/createproduct")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest entity) {
        log.info("createProduct invoked. Received request to create product: {}", entity);
        return productService.createProduct(entity);
    }
    
    @GetMapping("/getproducts")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        log.info("getAllProducts() invoked. Received request to fetch all products ");
        return productService.getAllProducts();
    }
}
