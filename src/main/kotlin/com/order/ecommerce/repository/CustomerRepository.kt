package com.order.ecommerce.repository

import com.order.ecommerce.model.Customer
import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<Customer, String>
