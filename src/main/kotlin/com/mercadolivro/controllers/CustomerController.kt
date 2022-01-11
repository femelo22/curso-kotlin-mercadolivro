package com.mercadolivro.controllers

import com.fasterxml.jackson.databind.util.BeanUtil
import com.mercadolivro.controllers.request.PostCustomerRequest
import com.mercadolivro.models.CustomerModel
import org.springframework.beans.BeanUtils
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customer")
class CustomerController {

    //"Mockando" um objeto
    val customers = mutableListOf<CustomerModel>()

    @GetMapping
    fun getAll(): MutableList<CustomerModel> {
        return customers
    }

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: Int): CustomerModel{
        return customers.filter { it.id == id }.first()
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody customer: PostCustomerRequest){

        val id = if(customers.isEmpty()){
            1
        }else{
            customers.last().id + 1;
        }

        customers.add(CustomerModel(id, customer.name, customer.email))

        println(customer)
    }

}