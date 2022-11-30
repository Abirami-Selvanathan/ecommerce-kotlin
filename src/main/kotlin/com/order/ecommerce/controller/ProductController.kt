package com.order.ecommerce.controller

import com.order.ecommerce.dto.ProductDto
import com.order.ecommerce.dto.ResponseDto
import com.order.ecommerce.enum.Status.SUCCESS
import com.order.ecommerce.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.webjars.NotFoundException
import javax.validation.ConstraintViolationException

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
class ProductController {

    @Autowired
    private lateinit var productService: ProductService

    @PostMapping
    @Operation(summary = "Create a product", description = "Create a product")
    fun create(@RequestBody productDto: ProductDto): ResponseEntity<ProductDto> {
        try {
            return ResponseEntity(productService.create(productDto), CREATED)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(BAD_REQUEST)
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product", description = "Get a product")
    fun fetch(@PathVariable(name = "id") id: Long): ResponseEntity<ProductDto> {
        try {
            return ResponseEntity(productService.getById(id), OK)
        } catch (e: NotFoundException) {
            throw ResponseStatusException(NOT_FOUND)
        }
    }

    @PatchMapping("/{id}")
    fun updateQuantity(
        @PathVariable("id") id: Long,
        @RequestParam(name = "quantity") quantity: Int
    ): ResponseEntity<ResponseDto> {
        try {
            productService.updateQuantity(id, quantity)
            val responseDto = ResponseDto(SUCCESS, "Updated quantity successfully")
            return ResponseEntity(responseDto, NO_CONTENT)
        } catch (e: NotFoundException) {
            throw ResponseStatusException(NOT_FOUND, e.message)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(BAD_REQUEST)
        }
    }

}
