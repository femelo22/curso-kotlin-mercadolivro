package com.mercadolivro.extension

import com.mercadolivro.controllers.request.PostCustomerRequest
import com.mercadolivro.controllers.request.PutCustomerRequest
import com.mercadolivro.models.CustomerModel

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(name = this.name, email = this.email)
}

fun PutCustomerRequest.toCustomerModel(id: Int): CustomerModel {
    return CustomerModel(id = id, name = this.name, email = this.email)
}
