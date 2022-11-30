package com.order.ecommerce.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.order.ecommerce.dto.ProductDto
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "product")
class Product : Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "sku")
    var sku: String? = null

    @Column(name = "title")
    var title: String? = null

    @Column(name = "description")
    var description: String? = null

    @Column(name = "price")
    var price: Double = 0.0

    @Column(name = "tax")
    var tax: Double = 0.0

    @Column(name = "quantity")
    var quantity: Int = 0

    @JsonIgnore
    @OneToMany(targetEntity = OrderItem::class, mappedBy = "product", fetch = FetchType.LAZY)
    var orderItems: List<OrderItem>? = null

    @Column(name = "created_at")
    @field:CreationTimestamp
    lateinit var createdAt: LocalDateTime

    @Column(name = "updated_at")
    @field:UpdateTimestamp
    lateinit var updatedAt: LocalDateTime

    fun toProductDto() = ProductDto(
        id = id,
        sku = sku ?: "",
        title = title ?: "",
        description = description ?: "",
        price = price,
        tax = tax,
        quantity = quantity
    )
}
