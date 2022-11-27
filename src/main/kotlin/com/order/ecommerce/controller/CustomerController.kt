package com.order.ecommerce.controller

import com.order.ecommerce.dto.CustomerDto
import com.order.ecommerce.model.Customer
import com.order.ecommerce.service.CustomerService
import io.swagger.v3.oas.annotations.Operation
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/customers")
@Slf4j
class CustomerController(val customerService: CustomerService) {

    @PostMapping
    @Operation(summary = "Create a customer", description = "Create a customer")
    fun createOrder(@RequestBody customerDto: CustomerDto): Customer {
        return customerService.create(customerDto)
    }
}
