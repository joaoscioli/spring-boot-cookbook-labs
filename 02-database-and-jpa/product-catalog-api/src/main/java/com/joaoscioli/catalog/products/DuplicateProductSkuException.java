package com.joaoscioli.catalog.products;

public class DuplicateProductSkuException extends RuntimeException {

    DuplicateProductSkuException(String sku) {
        super("Product SKU already exists: " + sku);
    }
}
