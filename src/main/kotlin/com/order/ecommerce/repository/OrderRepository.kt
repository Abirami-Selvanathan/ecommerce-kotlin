package com.order.ecommerce.repository

import com.order.ecommerce.model.Order
import org.springframework.data.repository.CrudRepository

interface OrderRepository : CrudRepository<Order, Long>
