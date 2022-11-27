package com.order.ecommerce.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.order.ecommerce.model.Customer
import lombok.NonNull
import java.time.LocalDateTime

data class CustomerDto(
    @JsonIgnore val id: String,
    @NonNull val name: String,
    @NonNull val phoneNumber: Number,
    @NonNull val email: String,
    @NonNull val password: String,
    @JsonIgnore val orders: MutableList<OrderDto>?,
    @JsonIgnore val createdAt: LocalDateTime?,
    @JsonIgnore val updatedAt: LocalDateTime?
)

fun CustomerDto.toCustomer(id: String) = Customer(
    id = id,
    name = name,
    phoneNumber = phoneNumber,
    email = email,
    password = password,
    orders = null,
    createdAt = createdAt ?: LocalDateTime.now(),
    updatedAt = updatedAt
)
