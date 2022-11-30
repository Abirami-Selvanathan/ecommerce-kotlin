package com.order.ecommerce.model

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.Data
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import com.order.ecommerce.enum.Role
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
@Data
class User : Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "name", nullable = false)
    var name: String? = null

    @Column(name = "email", nullable = false, unique = true)
    var email: String? = null

    @Column(name = "password", nullable = false)
    var password: String? = null

    @Column(name = "role", nullable = false)
    var role: Role? = null

    @JsonIgnore
    @OneToMany(targetEntity = Order::class, fetch = FetchType.EAGER , mappedBy = "user")
    var orders: List<Order>? = null

    @Column(name = "created_at")
    @field:CreationTimestamp
    lateinit var createdAt: LocalDateTime

    @Column(name = "updated_at")
    @field:UpdateTimestamp
    lateinit var updatedAt: LocalDateTime

}
