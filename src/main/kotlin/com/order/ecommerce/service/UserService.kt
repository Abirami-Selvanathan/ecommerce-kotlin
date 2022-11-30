package com.order.ecommerce.service

import com.order.ecommerce.dto.UserDto
import com.order.ecommerce.dto.toUser
import com.order.ecommerce.model.User
import com.order.ecommerce.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.webjars.NotFoundException
import javax.validation.ConstraintViolationException

@Component
class UserService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository

    companion object {
        val log: Logger = LoggerFactory.getLogger(ProductService::class.java)
    }

    fun create(userDto: UserDto): User {
        log.info("Saving user :: ${userDto.email}")
        val hashedPassword = BCryptPasswordEncoder().encode(userDto.password)
        userDto.password = hashedPassword
        return userRepository.save(userDto.toUser())
    }

    fun findById(userId: Long): User {
        log.info("Fetching user for user Id :: $userId")
        return userRepository.findById(userId).orElseThrow { NotFoundException("User not found") }
    }

    override fun loadUserByUsername(username: String): UserDetails {
        log.info("Loading user by username :: $username")
        val user = userRepository.findByEmail(username)

        val authorities = ArrayList<GrantedAuthority>()
        authorities.add(SimpleGrantedAuthority(user.role?.name ?: ""))

        return org.springframework.security.core.userdetails.User(user.email, user.password, authorities)
    }
}
