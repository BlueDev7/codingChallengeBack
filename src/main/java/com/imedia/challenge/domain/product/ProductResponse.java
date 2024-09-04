package com.imedia.challenge.domain.product;

import com.imedia.challenge.db.entity.ProductEntity;

import java.math.BigDecimal;

public class ProductResponse {

    private final String sku;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer stock;


    public ProductResponse(String sku, String name, String description, BigDecimal price, Integer stock) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public static ProductResponse fromProductEntity(ProductEntity product) {
        return new ProductResponse(
                product.getSku(),
                product.getName(),
                product.getDescription() != null ? product.getDescription() : "",
                product.getPrice(),
                product.getStock()
        );
    }
}

