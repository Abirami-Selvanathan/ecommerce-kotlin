package com.order.ecommerce.service

import com.order.ecommerce.dto.CustomerDto
import com.order.ecommerce.dto.toCustomer
import com.order.ecommerce.model.Customer
import com.order.ecommerce.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.UUID.randomUUID

@Service
class CustomerService(
    val customerRepository: CustomerRepository
) {
    fun create(customerDto: CustomerDto): Customer {
        return customerRepository.save(customerDto.toCustomer(randomUUID().toString()))
    }
}
