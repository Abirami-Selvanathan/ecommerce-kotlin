package com.order.ecommerce.controller

import com.order.ecommerce.service.ProductService
import com.order.ecommerce.util.ProductStub.Companion.buildProductDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus.*
import org.springframework.web.server.ResponseStatusException
import org.webjars.NotFoundException

private const val productId: Long = 1

private const val quantity = 1

@ExtendWith(MockitoExtension::class)
internal class ProductControllerTest {
    @InjectMocks
    lateinit var productController: ProductController

    @Mock
    lateinit var productService: ProductService

    @Test
    fun `should return created status code and user when valid productDto is given`() {
        val productDto = buildProductDto()

        `when`(productService.create(productDto)).thenReturn(productDto)

        val result = productController.create(productDto)

        assertEquals(CREATED, result.statusCode)
        verify(productService, atMostOnce()).create(productDto)
    }

    @Test
    fun `should throw ResponseStatusException when invalid userDto is given`() {
        val productDto = buildProductDto()

        `when`(productService.create(productDto)).thenThrow(IllegalArgumentException::class.java)

        assertThrows<ResponseStatusException> { productController.create(productDto) }
        verify(productService, atMostOnce()).create(productDto)
    }

    @Test
    fun `should fetch product when valid product id is given`() {
        val productDto = buildProductDto()

        `when`(productService.getById(productId)).thenReturn(productDto)

        val result = productController.fetch(productId)

        assertEquals(OK, result.statusCode)
        verify(productService, times(1)).getById(productId)
        verify(productService, atMostOnce()).getById(productId)
    }

    @Test
    fun `should throw ResponseStatusException when invalid product id is given`() {
        `when`(productService.getById(productId)).thenThrow(NotFoundException::class.java)

        assertThrows<ResponseStatusException> { productController.fetch(productId) }
        verify(productService, atMostOnce()).getById(productId)
    }

    @Test
    fun `should update quantity when valid id is given`() {
        doNothing().`when`(productService).updateQuantity(productId, quantity)

        val result = productController.updateQuantity(productId, quantity)

        assertEquals(NO_CONTENT, result.statusCode)
        verify(productService, atMostOnce()).updateQuantity(productId, quantity)
    }
}
