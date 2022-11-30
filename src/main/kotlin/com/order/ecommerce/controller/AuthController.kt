package com.order.ecommerce.controller

import com.order.ecommerce.config.JwtTokenUtil
import com.order.ecommerce.dto.AuthenticationRequestDto
import com.order.ecommerce.dto.AuthenticationResponseDto
import com.order.ecommerce.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/v1/authenticate")
class AuthenticationController {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userDetailsService: UserService

    @Autowired
    private val jwtTokenUtil: JwtTokenUtil? = null

    @PostMapping
    @Throws(Exception::class)
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequestDto): ResponseEntity<AuthenticationResponseDto> {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authenticationRequest.username, authenticationRequest.password
                )
            )
            val userDetails: UserDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)
            val token = jwtTokenUtil!!.generateToken(userDetails)
            return ResponseEntity.ok(AuthenticationResponseDto(token))
        } catch (e: AuthenticationException) {
            throw ResponseStatusException(BAD_REQUEST, "User disabled")
        }
    }
}
