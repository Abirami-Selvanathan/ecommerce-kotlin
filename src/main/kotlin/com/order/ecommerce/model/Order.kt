package com.order.ecommerce.model

import com.order.ecommerce.dto.OrderDto
import com.order.ecommerce.dto.OrderItemDto
import com.order.ecommerce.enum.OrderStatus
import lombok.Data
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
@Data
class Order : Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    var orderId: Long? = null

    @Column(name = "orderStatus")
    var orderStatus: OrderStatus? = null

    @Column(name = "total_amount")
    var totalAmount: Double = 0.0

    @Column(name = "shipping_charges")
    var shippingCharges: Double = 0.0

    @Column(name = "shipping_mode")
    var shippingMode: String? = null

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(referencedColumnName = "id", name = "payment_id")
    var payment: Payment? = null

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(referencedColumnName = "id", name = "shipping_address_id")
    var shippingAddress: Address?= null

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(referencedColumnName = "id", name = "billing_address_id")
    var billingAddress: Address? = null

    @OneToMany(targetEntity = OrderItem::class, fetch = FetchType.LAZY , mappedBy = "order", cascade = [CascadeType.ALL])
    var orderItems: List<OrderItem>? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    var user: User? = null

    @Column(name = "created_at")
    @field:CreationTimestamp
    lateinit var createdAt: LocalDateTime

    @Column(name = "updated_at")
    @field:UpdateTimestamp
    lateinit var updatedAt: LocalDateTime

    fun toOrderDto(): OrderDto {
        return OrderDto(
            userId = user?.id ?: 0,
            totalAmount = totalAmount,
            orderStatus = orderStatus,
            shippingCharges = shippingCharges,
            shippingMode = shippingMode ?: "",
            paymentDto = payment?.toPaymentDto(),
            billingAddress = billingAddress?.toAddressDto(),
            shippingAddress = shippingAddress?.toAddressDto(),
            orderItems = orderItems?.map { it.toOrderItemDto() }?.toMutableList() as List<OrderItemDto>
        )
    }
}
