package com.mercadolivro.controllers

import com.mercadolivro.models.CustomerModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("customer")
class CustomerController {

    @GetMapping
    fun listCustomer(): CustomerModel {
        return CustomerModel(1,"Fernando", "luiz123jfmg@gmail.com")
    }
}