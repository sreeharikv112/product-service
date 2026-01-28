package com.ecomm.dev.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ecomm.dev.productservice.dto.ProductRequest;
import com.ecomm.dev.productservice.dto.ProductResponse;
import com.ecomm.dev.productservice.model.Product;
import com.ecomm.dev.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        log.info("createProduct invoked. Received request to create product: {}", productRequest);
        if(productRequest == null){
            log.error("ProductRequest cannot be null" );
            throw new IllegalArgumentException("ProductRequest cannot be null");
        }else if(productRequest.name() == null || productRequest.name().isEmpty()){
            log.error("Product name cannot be null or empty" );
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }else if(productRequest.price() == null || productRequest.price().doubleValue() <= 0){
            log.error("Product price must be greater than zero");
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
        log.info("Parameters received in requests are valid, hence going ahead with product creation");

        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice()
        );
    }

    public List<ProductResponse> getAllProducts() {
        log.info("Fetching all products through product service getAllProducts method ");
        
        List<ProductResponse> response =  productRepository.findAll().
        stream()
        .map(product -> new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice()
        )).toList();

        log.info("Total {} products fetched", response.size());
        return response;    
    }

}
