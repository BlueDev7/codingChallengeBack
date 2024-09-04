package com.imedia.challenge.service;

import com.imedia.challenge.db.entity.ProductEntity;
import com.imedia.challenge.db.repository.ProductRepository;
import com.imedia.challenge.domain.product.ProductResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductService  {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    //Endpoint to find product by sku
    public ProductResponse findProductBySku(String sku) {
        Optional<ProductEntity> product = productRepository.findProductBySku(sku);
        return product.map(ProductResponse::fromProductEntity).orElse(null);
    }

    //Endpoint to add new product
    public void addProduct(ProductEntity product){
        Optional<ProductEntity> productEntity = productRepository.findProductBySku(product.getSku());
        if(productEntity.isPresent()){
            throw  new IllegalStateException("Sku is already taken");
        }
        productRepository.save(product);
    }

    //Endpoint to update a product
    @Transactional
    public void updateProduct(String sku, String name, String description, BigDecimal price){
        Optional<ProductEntity> productOptional  = productRepository.findById(sku);
        if(productOptional.isPresent()){
            ProductEntity product = productOptional.get();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
        }
    }


}
