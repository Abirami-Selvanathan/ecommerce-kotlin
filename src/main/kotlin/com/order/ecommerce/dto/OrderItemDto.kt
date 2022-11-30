package com.order.ecommerce.dto

import com.order.ecommerce.model.Order
import com.order.ecommerce.model.OrderItem
import com.order.ecommerce.model.Product

data class OrderItemDto(
    val productId: Long,
    val tax: Double,
    var subTotal: Double,
    var quantity: Int
)

fun OrderItemDto.toOrder(product: Product, order: Order): OrderItem {
    val orderItem = OrderItem()
    orderItem.tax = tax
    orderItem.subTotal = subTotal
    orderItem.product = product
    orderItem.order = order
    orderItem.quantity = quantity
    return orderItem
}
