package com.order.ecommerce.dto

import com.order.ecommerce.model.Address

data class AddressDto(
    val address1: String?,
    val address2: String?,
    val city: String?,
    val state: String?,
    val zip: Int?,
    val email: String?,
    val phone: Int?
)

fun AddressDto.toAddress(): Address{
    val address = Address()
    address.address1 = address1
    address.address2 = address2
    address.city = city
    address.state = state
    address.zip = zip
    address.email = email
    address.phone = phone
    return address
}
