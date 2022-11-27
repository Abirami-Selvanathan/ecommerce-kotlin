package com.order.ecommerce.controller

import com.order.ecommerce.dto.OrderCreateResponse
import com.order.ecommerce.dto.OrderDto
import com.order.ecommerce.model.Order
import com.order.ecommerce.service.OrderService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(val orderService: OrderService) {

    @PostMapping
    @Operation(summary = "Create an order", description = "Create an order")
    fun createOrder(@RequestBody orderDto: OrderDto): OrderCreateResponse {
        return orderService.createOrder(orderDto)
    }

    @GetMapping("/{orderId}")
    fun findOrderById(@PathVariable(name = "orderId") orderId: String): Order {
        return orderService.findOrderById(orderId)
    }

    @PatchMapping("/{orderId}")
    fun updateOrderStatus(
        @PathVariable("orderId") orderId: String,
        @RequestParam(name = "orderStatus") orderStatus: String
    ) {
        orderService.updateOrderStatus(orderId, orderStatus)
    }
}
