package com.order.ecommerce.repository

import com.order.ecommerce.model.OrderItem
import org.springframework.data.repository.CrudRepository

interface OrderItemRepository : CrudRepository<OrderItem, Long>
