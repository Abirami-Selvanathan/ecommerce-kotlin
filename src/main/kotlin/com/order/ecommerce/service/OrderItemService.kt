package com.order.ecommerce.service

import com.order.ecommerce.dto.OrderItemDto
import com.order.ecommerce.dto.OrderItemResponseDto
import com.order.ecommerce.dto.toOrder
import com.order.ecommerce.enum.OrderItemStatus
import com.order.ecommerce.enum.OrderItemStatus.*
import com.order.ecommerce.model.Order
import com.order.ecommerce.model.OrderItem
import com.order.ecommerce.model.Product
import com.order.ecommerce.repository.OrderItemRepository
import com.order.ecommerce.repository.ProductRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderItemService {

    @Autowired
    private lateinit var orderItemRepository: OrderItemRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    companion object {
        val log: Logger = LoggerFactory.getLogger(OrderService::class.java)
    }

    fun saveOrderItems(orderItemDtoList: List<OrderItemDto>, order: Order): List<OrderItemResponseDto> {
        val orderItemResponses: MutableList<OrderItemResponseDto> = mutableListOf()
        val orderItems: MutableList<OrderItem> = mutableListOf()

        for (orderItemDto in orderItemDtoList) {
            val productId: Long = orderItemDto.productId
            log.info("Create order item for product :: $productId")

            val product: Product
            var orderItemResponse: OrderItemResponseDto
            try {
                product = productRepository.findById(productId).orElseThrow()
            } catch (e: NoSuchElementException) {
                orderItemResponse = buildOrderItemResponse(productId, PRODUCT_NOT_FOUND)
                orderItemResponses.add(orderItemResponse)
                continue
            }

            orderItemResponse = validateAndGetOrderItemResponse(orderItemDto, product)
            orderItemResponses.add(orderItemResponse)

            if (orderItemResponse.status == AVAILABLE) {
                val subTotal = calculateSubTotal(product)
                orderItemDto.subTotal = subTotal

                val orderItem = orderItemDto.toOrder(product, order)
                orderItems.add(orderItem)
            }
        }
        if (orderItems.size > 0) {
            orderItemRepository.saveAll(orderItems)
        }
        return orderItemResponses
    }

    private fun calculateSubTotal(product: Product) =
        product.price.times(product.quantity).plus(product.tax)

    private fun validateAndGetOrderItemResponse(orderItemDto: OrderItemDto, product: Product): OrderItemResponseDto {
        val productId = product.id
        return if (isOutOfStock(product)) {
            buildOrderItemResponse(productId!!, OUT_OF_STOCK)
        } else if (isInsufficientQuantity(product, orderItemDto)) {
            buildOrderItemResponse(productId!!, INSUFFICIENT_QUANTITY_OF_STOCK)
        } else {
            buildOrderItemResponse(productId!!, AVAILABLE)
        }
    }

    private fun isInsufficientQuantity(
        product: Product,
        orderItemDto: OrderItemDto
    ) = product.quantity < orderItemDto.quantity

    private fun isOutOfStock(product: Product) = product.quantity <= 0

    private fun buildOrderItemResponse(productId: Long, orderItemStatus: OrderItemStatus) = OrderItemResponseDto(
        productId = productId,
        status = orderItemStatus
    )
}
