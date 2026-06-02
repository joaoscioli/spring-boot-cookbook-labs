# Product Catalog API

> Portfolio lab status: starting point.

Product Catalog API is a small Spring Boot lab focused on database modeling, JPA, Flyway migrations, validation, and HTTP API tests.

This project is intentionally smaller than the main `subscription-billing-api` portfolio project. Its role is to practice and document one specific backend capability: building a clean persistence-backed REST resource from the first commit.

## Current Scope

Implemented in the first slice:

- Spring Boot 3 project setup
- Java 21
- Product entity
- Product repository
- Product REST controller
- request validation
- Flyway migration
- H2 test profile
- MockMvc API tests

## API Surface

Create a product:

```http
POST /api/products
Content-Type: application/json

{
  "name": "Mechanical Keyboard",
  "sku": "keyboard-pro",
  "priceCents": 49900
}
```

List products:

```http
GET /api/products
```

Find product by SKU:

```http
GET /api/products/keyboard-pro
```

## Rules

- SKU must be unique.
- SKU must be lowercase and URL-friendly.
- Price must be greater than zero.

## Run Tests

```bash
mvn test
```

## Why This Lab Exists

This lab is a focused practice project for:

- relational schema design;
- entity mapping;
- validation;
- API behavior tests;
- small commit discipline.
