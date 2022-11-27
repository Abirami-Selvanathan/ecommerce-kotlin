package com.order.ecommerce.model

import lombok.Data
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "customer")
@Data
class Customer(
    @Id
    @Column(name = "id", unique = true, nullable = false)
    var id: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: Number,

    @Column(name = "email", nullable = false)
    var email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @OneToMany(targetEntity = Order::class, fetch = FetchType.LAZY, mappedBy = "customer")
    var orders: MutableList<Order>?,

    @Column(name = "created_at")
    @CreatedDate
    var createdAt: LocalDateTime,

    @Column(name = "updated_at")
    @LastModifiedDate
    var updatedAt: LocalDateTime?
) : Serializable
