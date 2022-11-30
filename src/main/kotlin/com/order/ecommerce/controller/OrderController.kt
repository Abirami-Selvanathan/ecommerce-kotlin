package com.order.ecommerce.controller

import com.order.ecommerce.dto.OrderDto
import com.order.ecommerce.dto.OrderResponseDto
import com.order.ecommerce.dto.ResponseDto
import com.order.ecommerce.enum.OrderStatus
import com.order.ecommerce.enum.Status
import com.order.ecommerce.service.OrderService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.webjars.NotFoundException

@RestController
@RequestMapping("/api/v1/orders")
class OrderController {

    @Autowired
    private lateinit var orderService: OrderService

    @PostMapping
    @Operation(summary = "Create an order", description = "Create an order")
    fun createOrder(@RequestBody orderDto: OrderDto): ResponseEntity<OrderResponseDto> {
        try {
            return ResponseEntity(orderService.createOrder(orderDto), CREATED)
        } catch (e: NotFoundException) {
            throw ResponseStatusException(NOT_FOUND, e.message)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(BAD_REQUEST)
        }
    }

    @GetMapping("/{orderId}")
    fun findOrderById(@PathVariable(name = "orderId") orderId: Long): ResponseEntity<OrderDto> {
        try {
            return ResponseEntity(orderService.findOrderById(orderId), OK)
        } catch (e: NotFoundException) {
            throw ResponseStatusException(NOT_FOUND, e.message)
        }
    }

    @PatchMapping("/{orderId}")
    fun updateOrderStatus(
        @PathVariable("orderId") orderId: Long,
        @RequestParam(name = "orderStatus") orderStatus: OrderStatus
    ): ResponseEntity<ResponseDto> {
        try {
            orderService.updateOrderStatus(orderId, orderStatus)
            val responseDto = ResponseDto(Status.SUCCESS, "Updated quantity successfully")
            return ResponseEntity(responseDto, NO_CONTENT)
        } catch (e: NotFoundException) {
            throw ResponseStatusException(NOT_FOUND, e.message)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(BAD_REQUEST)
        }
    }
}
