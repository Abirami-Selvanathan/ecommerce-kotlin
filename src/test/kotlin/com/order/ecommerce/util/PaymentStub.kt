package com.order.ecommerce.util

import com.order.ecommerce.dto.PaymentDto
import com.order.ecommerce.enum.PaymentMode
import com.order.ecommerce.enum.PaymentStatus

class PaymentStub {
    companion object {
        fun buildPaymentDto() = PaymentDto(
            amount = 10.0,
            paymentStatus = PaymentStatus.PROCESSING,
            paymentMode = PaymentMode.CREDIT,
            confirmationNumber = 1
        )
    }
}
