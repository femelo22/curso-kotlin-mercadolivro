package com.mercadolivro.controllers.request

import com.sun.istack.NotNull
import javax.validation.constraints.Positive

class PostPurchaseRequest(

    @field:NotNull
    @field:Positive
    val customerId: Int,

    @field:NotNull
    val booksId: Set<Int>
)