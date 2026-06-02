package com.joaoscioli.catalog.products;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(statements = "DELETE FROM products", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createProductReturnsCreatedProduct() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Mechanical Keyboard",
                                  "sku": "keyboard-pro",
                                  "priceCents": 49900
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Mechanical Keyboard"))
                .andExpect(jsonPath("$.sku").value("keyboard-pro"))
                .andExpect(jsonPath("$.priceCents").value(49900))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty());
    }

    @Test
    void listProductsReturnsCreatedProducts() throws Exception {
        createProduct("Mechanical Keyboard", "keyboard-pro", 49900);
        createProduct("USB-C Hub", "usb-c-hub", 19900);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void findProductBySkuReturnsProduct() throws Exception {
        createProduct("Mechanical Keyboard", "keyboard-pro", 49900);

        mockMvc.perform(get("/api/products/keyboard-pro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mechanical Keyboard"))
                .andExpect(jsonPath("$.sku").value("keyboard-pro"));
    }

    @Test
    void createProductRejectsDuplicateSku() throws Exception {
        createProduct("Mechanical Keyboard", "keyboard-pro", 49900);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Another Keyboard",
                                  "sku": "keyboard-pro",
                                  "priceCents": 59900
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Product SKU already exists: keyboard-pro"));
    }

    @Test
    void createProductRejectsInvalidPrice() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Free Sample",
                                  "sku": "free-sample",
                                  "priceCents": 0
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void findProductBySkuReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/products/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product not found: missing"));
    }

    private void createProduct(String name, String sku, long priceCents) throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "%s",
                                  "sku": "%s",
                                  "priceCents": %d
                                }
                                """.formatted(name, sku, priceCents)))
                .andExpect(status().isCreated());
    }
}
