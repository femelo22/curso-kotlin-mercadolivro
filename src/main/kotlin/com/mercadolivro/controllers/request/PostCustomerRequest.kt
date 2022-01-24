package com.mercadolivro.controllers.request

import com.mercadolivro.validations.EmailAvailable
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostCustomerRequest (

    @field:NotEmpty(message = "Nome deve ser informado")
    var name: String,

    @EmailAvailable
    var email: String

)