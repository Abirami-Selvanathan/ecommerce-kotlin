package com.order.ecommerce.dto

import com.order.ecommerce.enum.PaymentMode
import com.order.ecommerce.enum.PaymentStatus
import com.order.ecommerce.model.Payment

data class PaymentDto(

    val amount: Double,
    var paymentMode: PaymentMode?,
    val confirmationNumber: Number?,
    val paymentStatus: PaymentStatus?

) {
    fun toPayment(): Payment {
        val payment = Payment()
        payment.paymentMode = paymentMode
        payment.amount = amount
        payment.confirmationNumber = confirmationNumber
        payment.paymentStatus = paymentStatus
        return payment
    }
}
