package com.mercadolivro.controllers.request

import com.fasterxml.jackson.annotation.JsonAlias
import com.sun.istack.NotNull
import javax.validation.constraints.Positive

class PostPurchaseRequest(

    @field:NotNull
    @field:Positive
    @JsonAlias("customer_id")
    val customerId: Int,

    @field:NotNull
    @JsonAlias("book_ids")
    val booksId: Set<Int>
)
