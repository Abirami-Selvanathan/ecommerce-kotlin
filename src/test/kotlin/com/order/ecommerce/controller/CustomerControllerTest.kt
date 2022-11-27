package com.order.ecommerce.controller

import com.order.ecommerce.dto.CustomerDto
import com.order.ecommerce.model.Customer
import com.order.ecommerce.service.CustomerService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

private const val id = "123"
private const val name = "Name"
private const val email = "test@test.com"
private const val password = "password"
private val now = LocalDateTime.now()

@ExtendWith(MockitoExtension::class)
class CustomerControllerTest {
    @InjectMocks
    lateinit var customerController: CustomerController

    @Mock
    lateinit var customerService: CustomerService

    @Test
    fun `should create customer`() {
        val customerDto = buildCustomerDto()
        val customer = buildCustomer()
        `when`(customerService.create(customerDto)).thenReturn(customer)

        val result = customerController.createOrder(customerDto)

        assertThat(result).isEqualTo(customer)
    }

    private fun buildCustomer(): Customer =
        Customer(
            id = id,
            name = name,
            phoneNumber = 1234567890,
            email = email,
            password = password,
            orders = null,
            createdAt = now,
            updatedAt = null
        )

    private fun buildCustomerDto(): CustomerDto =
        CustomerDto(
            id = id,
            name = name,
            phoneNumber = 1234567890,
            email = email,
            password = password,
            orders = null,
            createdAt = null,
            updatedAt = null
        )
}
