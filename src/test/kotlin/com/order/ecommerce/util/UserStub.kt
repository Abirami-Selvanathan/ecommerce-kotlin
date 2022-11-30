package com.order.ecommerce.util

import com.order.ecommerce.dto.UserDto
import com.order.ecommerce.enum.Role

class UserStub {
    companion object {
        fun buildUserDto(): UserDto =
            UserDto(
                id = 1,
                name = "name",
                phoneNumber = 1234567890,
                email = "email@test.com",
                password = "password",
                role = Role.USER,
                orders = null,
                createdAt = null,
                updatedAt = null
            )
    }
}
