package com.joaoscioli.catalog.products;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository repository;

    ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductResponse create(@RequestBody @Valid CreateProductRequest request) {
        if (repository.existsBySku(request.sku())) {
            throw new DuplicateProductSkuException(request.sku());
        }

        var product = new Product(request.name().trim(), request.sku(), request.priceCents());
        return ProductResponse.from(repository.save(product));
    }

    @GetMapping
    List<ProductResponse> list() {
        return repository.findAll()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    @GetMapping("/{sku}")
    ProductResponse findBySku(@PathVariable String sku) {
        return repository.findBySku(sku)
                .map(ProductResponse::from)
                .orElseThrow(() -> new ProductNotFoundException(sku));
    }
}
