package com.order.ecommerce.repository

import com.order.ecommerce.model.Product
import org.springframework.data.repository.CrudRepository

interface ProductRepository : CrudRepository<Product, Long>
