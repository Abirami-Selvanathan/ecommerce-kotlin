package com.order.ecommerce.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.order.ecommerce.enum.Role
import com.order.ecommerce.model.User
import java.time.LocalDateTime

data class UserDto(
    @JsonIgnore val id: Long?,
    val name: String,
    val phoneNumber: Number,
    val email: String,
    var password: String,
    @JsonIgnore val orders: MutableList<OrderDto>?,
    val role: Role,
    @JsonIgnore val createdAt: LocalDateTime?,
    @JsonIgnore val updatedAt: LocalDateTime?
)

fun UserDto.toUser(): User {
    val user = User()
    user.name = name
    user.email = email
    user.password = password
    user.role = role
    return user
}
