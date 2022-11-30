package com.order.ecommerce.controller

import com.order.ecommerce.model.User
import com.order.ecommerce.service.UserService
import com.order.ecommerce.util.UserStub.Companion.buildUserDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.server.ResponseStatusException

@ExtendWith(MockitoExtension::class)
internal class UserControllerTest {
    @InjectMocks
    lateinit var userController: UserController

    @Mock
    lateinit var userService: UserService

    @Test
    fun `should return created status code and user when valid userDto is given`() {
        val userDto = buildUserDto()
        val user = User()
        `when`(userService.create(userDto)).thenReturn(user)

        val result = userController.create(userDto)

        assertEquals(result.statusCode, CREATED)
        assertEquals(user, result.body)
        verify(userService, atMostOnce()).create(userDto)
    }

    @Test
    fun `should return bad request when invalid userDto is given`() {
        val userDto = buildUserDto()

        `when`(userService.create(userDto)).thenThrow(IllegalArgumentException::class.java)

        assertThrows<ResponseStatusException> { userController.create(userDto) }
        verify(userService, atMostOnce()).create(userDto)
    }
}
