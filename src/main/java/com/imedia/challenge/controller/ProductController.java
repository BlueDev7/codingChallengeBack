package com.imedia.challenge.controller;

import com.imedia.challenge.db.entity.ProductEntity;
import com.imedia.challenge.domain.product.ProductResponse;
import com.imedia.challenge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping(path = "{sku}",produces = "application/json;charset=utf-8")
    public ResponseEntity<ProductResponse> findProductsBySku(@PathVariable("sku") String sku){
        ProductResponse product = productService.findProductBySku(sku);
        return product == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProductsByList(@RequestParam List<String> skus){
        List<ProductResponse> productResponses = new ArrayList<>() ;

        for (String sku : skus){
            ProductResponse productResponse = productService.findProductBySku(sku);
            if(productResponse != null){
                productResponses.add(productResponse);
            }
        }

        if (productResponses.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productResponses);
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody ProductEntity product){
        productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(path = "{sku}")
    public ResponseEntity<Void> updateProduct(@PathVariable("sku") String sku,
                                              @RequestParam String name,
                                              @RequestParam String description,
                                              @RequestParam BigDecimal price){
         productService.updateProduct(sku,name,description,price);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
