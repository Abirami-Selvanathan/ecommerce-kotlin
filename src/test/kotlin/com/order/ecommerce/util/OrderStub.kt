package com.order.ecommerce.util

import com.order.ecommerce.dto.OrderDto
import com.order.ecommerce.dto.OrderItemDto
import com.order.ecommerce.dto.OrderItemResponseDto
import com.order.ecommerce.dto.OrderResponseDto
import com.order.ecommerce.enum.OrderItemStatus.PRODUCT_NOT_FOUND
import com.order.ecommerce.enum.OrderStatus.PROCESSING
import com.order.ecommerce.util.AddressStub.Companion.buildAddressDto
import com.order.ecommerce.util.PaymentStub.Companion.buildPaymentDto

class OrderStub {

    companion object {

        fun buildOrderItemResponse() = OrderItemResponseDto(productId = 1, status = PRODUCT_NOT_FOUND)

        fun buildOrderResponse() =
            OrderResponseDto(orderId = 1, orderStatus = PROCESSING, orderItemResponses = mutableListOf())

        fun buildOrderItemDto() = OrderItemDto(productId = 1, tax = 10.0, subTotal = 100.0, quantity = 1)

        fun buildOrderDto(orderItemDtoList: List<OrderItemDto>) =
            OrderDto(
                userId = 1,
                orderStatus = PROCESSING,
                totalAmount = 10.0,
                shippingCharges = 10.0,
                shippingMode = "BIKE",
                paymentDto = buildPaymentDto(),
                billingAddress = buildAddressDto(),
                shippingAddress = buildAddressDto(),
                orderItems = orderItemDtoList
            )
    }
}
