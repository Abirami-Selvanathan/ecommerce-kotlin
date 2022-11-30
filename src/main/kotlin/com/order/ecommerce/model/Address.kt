package com.order.ecommerce.model

import com.order.ecommerce.dto.AddressDto
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "Address")
class Address {
    @Id
    @GeneratedValue
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "address1")
    var address1: String? = null

    @Column(name = "address2")
    var address2: String? = null

    @Column(name = "city")
    var city: String? = null

    @Column(name = "state")
    var state: String? = null

    @Column(name = "zip")
    var zip: Int? = null

    @Column(name = "email")
    var email: String? = null

    @Column(name = "phone")
    var phone: Int? = null

    @Column(name = "created_at")
    @field:CreationTimestamp
    lateinit var createdAt: LocalDateTime

    @Column(name = "updated_at")
    @field:UpdateTimestamp
    lateinit var updatedAt: LocalDateTime

    fun toAddressDto(): AddressDto {
        return AddressDto(
            address1 = address1,
            address2 = address2,
            city = city,
            state = state,
            zip = zip,
            email = email,
            phone = phone
        )
    }
}
