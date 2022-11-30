package com.order.ecommerce.service

import com.order.ecommerce.model.Product
import com.order.ecommerce.repository.ProductRepository
import com.order.ecommerce.util.ProductStub
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.webjars.NotFoundException
import java.util.Optional.of

@ExtendWith(MockitoExtension::class)
internal class ProductServiceTest {
    @InjectMocks
    lateinit var productService: ProductService

    @Mock
    lateinit var productRepository: ProductRepository

    @Test
    fun `create product`() {
        val productDto = ProductStub.buildProductDto()
        val product = Product()
        product.title = productDto.title

        `when`(productRepository.save(any())).thenReturn(product)

        val result = productService.create(productDto)

        assertEquals(result.title, productDto.title)
    }

    @Test
    fun `return product when valid product id is given`() {
        val productId: Long = 1
        val product = Product()
        product.id = productId

        `when`(productRepository.findById(productId)).thenReturn(of(product))

        val result = productService.getById(productId)

        assertEquals(productId, result.id)
    }

    @Test
    fun `should throw not found exception when invalid id is given`() {
        val productId: Long = 1
        val product = Product()
        product.id = productId

        `when`(productRepository.findById(productId)).thenThrow(NotFoundException::class.java)

        assertThrows<NotFoundException> { productService.getById(productId) }
    }

    @Test
    fun `should update quantity when valid product id is given`() {
        val existingQuantity = 0
        val updatedQuantity = 1
        val productId: Long = 1
        val product = Product()
        product.quantity = existingQuantity

        `when`(productRepository.findById(productId)).thenReturn(of(product))

        productService.updateQuantity(productId, updatedQuantity)

        product.quantity = updatedQuantity
        verify(productRepository, atMostOnce()).save(product)
    }

    @Test
    fun `should throw not found exception when invalid id is given during quantity update`() {
        val productId: Long = 1
        val quantity = 1
        val product = Product()
        product.id = productId

        `when`(productRepository.findById(productId)).thenThrow(NotFoundException::class.java)

        assertThrows<NotFoundException> { productService.updateQuantity(productId, quantity) }
    }
}
