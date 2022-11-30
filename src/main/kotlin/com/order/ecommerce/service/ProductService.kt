package com.order.ecommerce.service

import com.order.ecommerce.dto.ProductDto
import com.order.ecommerce.dto.toProduct
import com.order.ecommerce.model.Product
import com.order.ecommerce.repository.ProductRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.webjars.NotFoundException

@Service
class ProductService {

    @Autowired
    private lateinit var  productRepository: ProductRepository

    companion object {
        val log: Logger = getLogger(ProductService::class.java)
    }

    fun create(productDto: ProductDto): ProductDto {
        log.info("Creating product for ${productDto.title}")
        val product = productRepository.save(productDto.toProduct())
        return product.toProductDto()
    }

    fun getById(productId: Long): ProductDto {
        log.info("Fetching product by productId :: $productId")
        val product = productRepository.findById(productId).orElseThrow { NotFoundException("Product not found") }
        return product.toProductDto()
    }

    fun updateQuantity(id: Long, quantity: Int) {
        log.info("Fetching product by productId :: $id")
        val product: Product = productRepository.findById(id).orElseThrow()
        product.quantity = quantity
        log.info("Updating order quantity :: $quantity")
        productRepository.save(product)
    }
}
