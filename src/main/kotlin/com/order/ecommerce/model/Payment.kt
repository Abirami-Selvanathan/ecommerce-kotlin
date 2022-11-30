package com.order.ecommerce.model

import com.order.ecommerce.dto.PaymentDto
import com.order.ecommerce.enum.PaymentMode
import com.order.ecommerce.enum.PaymentStatus
import lombok.Data
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "payment")
@Data
class Payment : Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "amount")
    var amount: Double = 0.0

    @Column(name = "payment_mode")
    var paymentMode: PaymentMode? = null

    @Column(name = "confirmation_number")
    var confirmationNumber: Number? = null

    @Column(name = "payment_status")
    var paymentStatus: PaymentStatus? = null

    @Column(name = "created_at")
    @field:CreationTimestamp
    lateinit var createdAt: LocalDateTime

    @Column(name = "updated_at")
    @field:UpdateTimestamp
    lateinit var updatedAt: LocalDateTime

    fun toPaymentDto(): PaymentDto = PaymentDto(
        amount = amount,
        paymentMode = paymentMode,
        confirmationNumber = confirmationNumber,
        paymentStatus = paymentStatus
    )
}
