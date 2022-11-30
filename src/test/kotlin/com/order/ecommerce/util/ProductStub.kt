package com.order.ecommerce.util

import com.order.ecommerce.dto.AddressDto
import com.order.ecommerce.dto.ProductDto
import com.order.ecommerce.model.Product

class ProductStub {
    companion object {
        fun buildProductDto() = ProductDto(
            id = 1,
            sku = "M",
            title = "title",
            quantity = 1,
            tax = 10.0,
            description = "description",
            price = 10.0
        )
    }
}
