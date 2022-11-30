package com.order.ecommerce.dto

import com.order.ecommerce.enum.OrderItemStatus

data class OrderItemResponseDto(
    val productId: Long,
    val status: OrderItemStatus?
)
