package com.order.ecommerce.dto

import com.order.ecommerce.model.Product

data class ProductDto(
    val id: Long?,
    val sku: String,
    val title: String,
    val quantity: Int,
    val tax: Double,
    val description: String,
    val price: Double,
)

fun ProductDto.toProduct(): Product {
    val product = Product()
    product.sku = sku
    product.title = title
    product.description = description
    product.price = price
    product.quantity = quantity
    product.tax = tax
    return product
}
