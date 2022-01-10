package com.mercadolivro.controllers

import com.mercadolivro.controllers.request.PostCustomerRequest
import com.mercadolivro.models.CustomerModel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customer")
class CustomerController {

    @GetMapping
    fun listCustomer(): CustomerModel {
        return CustomerModel(1,"Fernando", "luiz123jfmg@gmail.com")
    }

    @PostMapping
    fun create(@RequestBody customer: PostCustomerRequest){
        println(customer)
    }

}