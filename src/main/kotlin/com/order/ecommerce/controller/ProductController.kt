package com.order.ecommerce.controller

import com.order.ecommerce.dto.ProductDto
import com.order.ecommerce.model.Product
import com.order.ecommerce.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
class ProductController(val productService: ProductService) {

    @PostMapping
    @Operation(summary = "Create a product", description = "Create a product")
    fun createOrder(@RequestBody productDto: ProductDto): Product {
        return productService.createProduct(productDto)
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get a product", description = "Get a product")
    fun getProduct(@PathVariable(name = "productId") productId: String): Product {
        return productService.getProduct(productId)
    }

}
