package com.order.ecommerce.service

import com.order.ecommerce.enum.OrderItemStatus.*
import com.order.ecommerce.model.Order
import com.order.ecommerce.model.OrderItem
import com.order.ecommerce.model.Product
import com.order.ecommerce.repository.OrderItemRepository
import com.order.ecommerce.repository.ProductRepository
import com.order.ecommerce.util.OrderStub.Companion.buildOrderItemDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional.of

private const val productId: Long = 1

@ExtendWith(MockitoExtension::class)
internal class OrderItemServiceTest {
    @InjectMocks
    lateinit var orderItemService: OrderItemService

    @Mock
    lateinit var orderItemRepository: OrderItemRepository

    @Mock
    lateinit var productRepository: ProductRepository

    @Test
    fun `should save order items and return order item response when order quantity available for product`() {
        val quantity = 2

        val product = Product()
        product.id = productId
        product.quantity = quantity
        product.tax = 10.0
        product.price = 10.0
        val orderItemDto = buildOrderItemDto()
        orderItemDto.quantity = quantity
        val orderItems = listOf<OrderItem>()

        `when`(productRepository.findById(productId)).thenReturn(of(product))
        `when`(orderItemRepository.saveAll(anyList())).thenReturn(orderItems)

        val result = orderItemService.saveOrderItems(listOf(orderItemDto), Order())

        assertEquals(result[0].status, AVAILABLE)
        verify(orderItemRepository, atMostOnce()).saveAll(anyList())
        verify(productRepository, atMostOnce()).findById(productId)
    }

    @Test
    fun `should return product not found when product is not available`() {
        val quantity = 1

        val order = Order()
        val orderItemDto = buildOrderItemDto()
        orderItemDto.quantity = quantity

        `when`(productRepository.findById(productId)).thenThrow(NoSuchElementException::class.java)

        val result = orderItemService.saveOrderItems(listOf(orderItemDto), order)

        assertEquals(result[0].status, PRODUCT_NOT_FOUND)
        verify(productRepository, atMostOnce()).findById(productId)
        verify(orderItemRepository, never()).saveAll(anyList())
    }

    @Test
    fun `should return insufficient quantity of stock when product does not have sufficient quantity`() {
        val productQuantity = 1
        val orderQuantity = 2

        val product = Product()
        product.id = 1
        product.quantity = productQuantity
        product.tax = 10.0
        val orderItemDto = buildOrderItemDto()
        orderItemDto.quantity = orderQuantity

        `when`(productRepository.findById(1)).thenReturn(of(product))

        val result = orderItemService.saveOrderItems(listOf(orderItemDto), Order())

        assertEquals(result[0].status, INSUFFICIENT_QUANTITY_OF_STOCK)
        verify(orderItemRepository, never()).saveAll(anyList())
        verify(productRepository, atMostOnce()).findById(productId)
    }

    @Test
    fun `should return out of stock when product quantity is zero`() {
        val productQuantity = 0
        val orderQuantity = 2

        val product = Product()
        product.id = 1
        product.quantity = productQuantity
        product.tax = 10.0
        val orderItemDto = buildOrderItemDto()
        orderItemDto.quantity = orderQuantity

        `when`(productRepository.findById(1)).thenReturn(of(product))

        val result = orderItemService.saveOrderItems(listOf(orderItemDto), Order())

        assertEquals(result[0].status, OUT_OF_STOCK)
        verify(orderItemRepository, never()).saveAll(anyList())
        verify(productRepository, atMostOnce()).findById(productId)
    }
}
