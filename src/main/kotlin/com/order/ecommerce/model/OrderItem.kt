package com.order.ecommerce.model

import com.order.ecommerce.dto.OrderItemDto
import lombok.Data
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "order_item")
@Data
class OrderItem : Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "tax")
    var tax: Double = 0.0

    @Column(name = "subTotal")
    var subTotal: Double = 0.0

    @Column(name = "quantity")
    var quantity: Int = 0

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(referencedColumnName = "id", name = "product_id")
    var product: Product? = null

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(referencedColumnName = "id", name = "order_id")
    var order: Order? = null

    @Column(name = "created_at")
    @field:CreationTimestamp
    lateinit var createdAt: LocalDateTime

    @Column(name = "updated_at")
    @field:UpdateTimestamp
    lateinit var updatedAt: LocalDateTime

    fun toOrderItemDto(): OrderItemDto? {
        return OrderItemDto(productId = product?.id ?: 0, tax = tax, subTotal = subTotal, quantity = quantity)
    }
}
