package com.order.ecommerce.repository

import com.order.ecommerce.model.User
import org.springframework.data.repository.CrudRepository
import javax.validation.ConstraintViolationException

interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String): User

    fun save(user: User): User
}
