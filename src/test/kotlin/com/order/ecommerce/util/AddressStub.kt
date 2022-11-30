package com.order.ecommerce.util

import com.order.ecommerce.dto.AddressDto

class AddressStub {
    companion object {
        fun buildAddressDto() = AddressDto(
            address1 = "one",
            address2 = "two",
            city = "Chennai",
            state = "TN",
            zip = 600048,
            email = "test@test.com",
            phone = 1234567890
        )
    }
}
