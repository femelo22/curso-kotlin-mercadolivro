package com.mercadolivro.controllers.request

import java.math.BigDecimal
import javax.validation.constraints.NotEmpty

data class PutBookRequest (

    @field:NotEmpty(message = "Nome deve ser informado")
    var name: String?,

    @field:NotEmpty(message = "Preco deve ser informado")
    var price: BigDecimal?
)