package com.mercadolivro.models

import com.mercadolivro.enuns.BookStatus
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "book")
data class BookModel(

    //Como alguns atributos são nulable, colocamos o valor padrão como nulo
    // "? = null"  --> isso permite instanciar a entidade sem passar valores
    // como se fosse o construtor padrão

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,

    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel?

)