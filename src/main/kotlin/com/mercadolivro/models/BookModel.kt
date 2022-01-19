package com.mercadolivro.models

import com.mercadolivro.enuns.BookStatus
import java.awt.print.Book
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

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel?

){
    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value) {
            if(field == BookStatus.DELETADO || field == BookStatus.CENCELADO){
                throw Exception("Não é possivel alterar um livro com status ${field}")
            }
            field = value
        }

    constructor(id: Int? = null, name: String, price: BigDecimal, customer: CustomerModel? = null, status: BookStatus?): this(id, name, price, customer) {
        this.status = status
    }
}