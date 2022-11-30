package com.order.ecommerce.service

import com.order.ecommerce.enum.OrderStatus.PROCESSING
import com.order.ecommerce.model.Order
import com.order.ecommerce.model.User
import com.order.ecommerce.repository.OrderRepository
import com.order.ecommerce.util.OrderStub.Companion.buildOrderDto
import com.order.ecommerce.util.OrderStub.Companion.buildOrderItemDto
import com.order.ecommerce.util.OrderStub.Companion.buildOrderItemResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.webjars.NotFoundException

@ExtendWith(MockitoExtension::class)
internal class OrderServiceTest {
    @InjectMocks
    lateinit var orderService: OrderService

    @Mock
    lateinit var userService: UserService

    @Mock
    lateinit var orderRepository: OrderRepository

    @Mock
    lateinit var orderItemService: OrderItemService

    @Test
    fun `should throw User not found when invalid userId is given`() {
        val userId: Long = 1
        val orderDto = buildOrderDto(mutableListOf())

        `when`(userService.findById(userId)).thenThrow(NotFoundException::class.java)

        assertThrows<NotFoundException> { orderService.createOrder(orderDto) }
    }

    @Test
    fun `should return OrderResponse when valid OrderDto is given`() {
        val orderId: Long = 123
        val orderStatus = PROCESSING
        val user = User()

        val orderItemResponse = buildOrderItemResponse()
        val orderItemResponses = listOf(orderItemResponse)
        val orderItemDto = buildOrderItemDto()
        val orderItemDtoList = listOf(orderItemDto)
        val orderDto = buildOrderDto(orderItemDtoList)
        val order = Order()
        order.orderId = orderId
        order.orderStatus = orderStatus

        `when`(userService.findById(orderDto.userId)).thenReturn(user)
        `when`(orderRepository.save(any())).thenReturn(order)
        `when`(orderItemService.saveOrderItems(orderDto.orderItems, order)).thenReturn(orderItemResponses)

        val result = orderService.createOrder(orderDto)

        assertEquals(result.orderId, orderId)
        assertEquals(result.orderStatus, orderStatus)
        assertEquals(result.orderItemResponses, orderItemResponses)
        verify(userService, atMostOnce()).findById(orderDto.userId)
        verify(orderRepository, atMostOnce()).save(any())
        verify(orderItemService, atMostOnce()).saveOrderItems(orderDto.orderItems, order)
    }
}
