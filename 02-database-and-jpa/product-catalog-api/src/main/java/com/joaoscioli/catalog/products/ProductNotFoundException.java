package com.joaoscioli.catalog.products;

public class ProductNotFoundException extends RuntimeException {

    ProductNotFoundException(String sku) {
        super("Product not found: " + sku);
    }
}
