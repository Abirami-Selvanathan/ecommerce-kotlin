package com.order.ecommerce.controller

import com.order.ecommerce.dto.UserDto
import com.order.ecommerce.model.User
import com.order.ecommerce.service.UserService
import io.swagger.v3.oas.annotations.Operation
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import javax.validation.ConstraintViolationException

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping
    @Operation(summary = "Create a user", description = "Create a user")
    fun create(@RequestBody userDto: UserDto): ResponseEntity<User> {
        return try {
            ResponseEntity(userService.create(userDto), CREATED)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(BAD_REQUEST)
        } catch (e: DataIntegrityViolationException) {
            throw ResponseStatusException(FORBIDDEN, "Email already exists")
        }
    }
}
