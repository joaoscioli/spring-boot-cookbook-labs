package com.joaoscioli.catalog.products;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String sku,
        long priceCents,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getPriceCents(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
