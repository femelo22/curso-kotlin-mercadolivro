package com.mercadolivro.controllers.response

import com.mercadolivro.enuns.CustomerStatus

data class CustomerResponse (

    var id: Int? = null,

    var name: String,

    var email: String,

    var status: CustomerStatus
)
