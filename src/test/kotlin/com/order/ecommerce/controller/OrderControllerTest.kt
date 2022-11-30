package com.order.ecommerce.controller

import com.order.ecommerce.enum.OrderStatus.PROCESSING
import com.order.ecommerce.service.OrderService
import com.order.ecommerce.util.OrderStub.Companion.buildOrderDto
import com.order.ecommerce.util.OrderStub.Companion.buildOrderItemDto
import com.order.ecommerce.util.OrderStub.Companion.buildOrderResponse
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

private const val orderId: Long = 1

@ExtendWith(MockitoExtension::class)
internal class OrderControllerTest {

    @InjectMocks
    lateinit var orderController: OrderController

    @Mock
    lateinit var orderService: OrderService

    @Test
    fun `should throw response status exception when NotFoundException occurs`() {
        val orderItemDto = buildOrderItemDto()
        val orderItemDtoList = listOf(orderItemDto)
        val orderDto = buildOrderDto(orderItemDtoList)

        `when`(orderService.createOrder(orderDto)).thenThrow(NotFoundException::class.java)

        assertThrows<ResponseStatusException> { orderController.createOrder(orderDto) }
        verify(orderService, atMostOnce()).createOrder(orderDto)
    }

    @Test
    fun `should throw response status exception when IllegalArgumentException occurs`() {
        val orderItemDto = buildOrderItemDto()
        val orderItemDtoList = listOf(orderItemDto)
        val orderDto = buildOrderDto(orderItemDtoList)

        `when`(orderService.createOrder(orderDto)).thenThrow(NotFoundException::class.java)

        assertThrows<ResponseStatusException> { orderController.createOrder(orderDto) }
        verify(orderService, atMostOnce()).createOrder(orderDto)
    }

    @Test
    fun `should create order`() {
        val orderItemDto = buildOrderItemDto()
        val orderItemDtoList = listOf(orderItemDto)
        val orderDto = buildOrderDto(orderItemDtoList)
        val orderResponse = buildOrderResponse()

        `when`(orderService.createOrder(orderDto)).thenReturn(orderResponse)

        val actualResponse = orderController.createOrder(orderDto)

        assertEquals(actualResponse.statusCode, CREATED)
        verify(orderService, atMostOnce()).createOrder(orderDto)
    }

    @Test
    fun `should return order when valid order is given`() {
        val orderId: Long = 1
        val orderItemDto = buildOrderItemDto()
        val orderItemDtoList = listOf(orderItemDto)
        val orderDto = buildOrderDto(orderItemDtoList)

        `when`(orderService.findOrderById(orderId)).thenReturn(orderDto)

        val actualResponse = orderController.findOrderById(orderId)

        assertEquals(actualResponse.statusCode, OK)
        verify(orderService, atMostOnce()).createOrder(orderDto)
    }

    @Test
    fun `should throw ResponseStatusException order when invalid order is given`() {
        `when`(orderService.findOrderById(orderId)).thenThrow(NotFoundException::class.java)

        assertThrows<ResponseStatusException> { orderController.findOrderById(orderId) }
        verify(orderService, atMostOnce()).findOrderById(orderId)
    }

    @Test
    fun `should update order status`() {
        doNothing().`when`(orderService).updateOrderStatus(orderId, PROCESSING)

        val actualResponse = orderController.updateOrderStatus(orderId, PROCESSING)

        assertEquals(actualResponse.statusCode, NO_CONTENT)
        verify(orderService, atMostOnce()).findOrderById(orderId)
    }

    @Test
    fun `should throw ResponseStatusException when Not found exception occurs`() {
        doThrow(NotFoundException::class.java).`when`(orderService).updateOrderStatus(orderId, PROCESSING)

        assertThrows<ResponseStatusException> { orderController.updateOrderStatus(orderId, PROCESSING) }
        verify(orderService, atMostOnce()).updateOrderStatus(orderId, PROCESSING)
    }

    @Test
    fun `should throw ResponseStatusException when Illegal argument exception occurs`() {
        doThrow(IllegalArgumentException::class.java).`when`(orderService).updateOrderStatus(orderId, PROCESSING)

        assertThrows<ResponseStatusException> { orderController.updateOrderStatus(orderId, PROCESSING) }
        verify(orderService, atMostOnce()).updateOrderStatus(orderId, PROCESSING)
    }
}
