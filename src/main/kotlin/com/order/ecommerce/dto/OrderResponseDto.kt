package com.order.ecommerce.dto

import com.order.ecommerce.enum.OrderStatus

data class OrderResponseDto(
    val orderId: Long?,
    val orderStatus: OrderStatus?,
    val orderItemResponses: List<OrderItemResponseDto>?
)
