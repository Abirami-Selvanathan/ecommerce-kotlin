package com.order.ecommerce.utils

import com.order.ecommerce.exception.CustomException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.xml.bind.DatatypeConverter

private const val MD5 = "MD5"

class PasswordEncrypt {
    companion object {
        fun hashPassword(password: String): String {
            return try {
                val md = MessageDigest.getInstance(MD5)
                md.update(password.toByte())
                val digest = md.digest()
                DatatypeConverter.printHexBinary(digest).uppercase(Locale.getDefault())
            } catch (e: NoSuchAlgorithmException) {
                throw CustomException("Password hashing failed")
            }
        }
    }
}
