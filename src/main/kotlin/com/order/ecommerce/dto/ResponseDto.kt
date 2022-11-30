package com.order.ecommerce.dto

import com.order.ecommerce.enum.Status

data class ResponseDto(val success: Status, val message: String)
