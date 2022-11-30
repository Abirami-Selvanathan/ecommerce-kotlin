package com.order.ecommerce.service

import com.order.ecommerce.dto.*
import com.order.ecommerce.enum.OrderStatus
import com.order.ecommerce.enum.PaymentStatus.PROCESSING
import com.order.ecommerce.model.Order
import com.order.ecommerce.repository.OrderRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.webjars.NotFoundException
import javax.transaction.Transactional

@Service
class OrderService {

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var orderItemService: OrderItemService

    @Autowired
    private lateinit var userService: UserService

    companion object {
        val log: Logger = getLogger(OrderService::class.java)
    }

    fun updateOrderStatus(orderId: Long, orderStatus: OrderStatus) {
        log.info("Fetching orderId :: $orderId")
        val order: Order = orderRepository.findById(orderId).orElseThrow { NotFoundException("Order not found") }
        order.orderStatus = orderStatus
        log.info("Updating order status :: $orderStatus")
        orderRepository.save(order)
    }

    fun findOrderById(orderId: Long): OrderDto {
        log.info("Fetching orderId :: $orderId")
        val order = orderRepository.findById(orderId).orElseThrow { NotFoundException("Order not found") }
        return order.toOrderDto()
    }

    @Transactional
    fun createOrder(orderDto: OrderDto): OrderResponseDto {
        val userId = orderDto.userId
        val user = userService.findById(userId)
        log.info("Creating order for user :: $userId")

        val billingAddress = orderDto.billingAddress?.toAddress()
        val shippingAddress = orderDto.shippingAddress?.toAddress()
        val payment = orderDto.paymentDto?.toPayment()
        payment?.paymentStatus = PROCESSING

        val order = orderDto.toOrder(billingAddress, shippingAddress, user, payment)
        val savedOrder: Order = orderRepository.save(order)

        val orderItemResponses: List<OrderItemResponseDto> =
            orderItemService.saveOrderItems(orderDto.orderItems, savedOrder)

        return OrderResponseDto(savedOrder.orderId, savedOrder.orderStatus, orderItemResponses)
    }
}
