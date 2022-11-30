package com.order.ecommerce.dto

import com.order.ecommerce.enum.OrderStatus
import com.order.ecommerce.model.Address
import com.order.ecommerce.model.Order
import com.order.ecommerce.model.Payment
import com.order.ecommerce.model.User

data class OrderDto(
    var userId: Long,
    var totalAmount: Double,
    var orderStatus: OrderStatus?,
    var shippingCharges: Double,
    var shippingMode: String?,
    var paymentDto: PaymentDto?,
    var billingAddress: AddressDto?,
    var shippingAddress: AddressDto?,
    var orderItems: List<OrderItemDto>
)

fun OrderDto.toOrder(
    billingAddress: Address?,
    shippingAddress: Address?,
    user: User?,
    payment: Payment?
): Order {
    val order = Order()
    order.orderStatus = orderStatus
    order.totalAmount = totalAmount
    order.shippingCharges = shippingCharges
    order.shippingMode = shippingMode
    order.payment = payment
    order.billingAddress = billingAddress
    order.shippingAddress = shippingAddress
    if (user != null) {
        order.user = user
    }
    return order
}
