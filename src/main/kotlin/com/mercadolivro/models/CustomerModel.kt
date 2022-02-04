package com.mercadolivro.models

import com.mercadolivro.enuns.CustomerStatus
import com.mercadolivro.enuns.Profile
import javax.persistence.*

@Entity(name = "customer")
data class CustomerModel (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var email: String,

    @Column
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus,

    @Column
    val password: String,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "customer_roles", joinColumns = [JoinColumn(name = "customer_id")])
    @ElementCollection(targetClass = Profile::class, fetch = FetchType.EAGER)
    val roles: Set<Profile> = setOf() //começando com valor vazio de padrão
)

