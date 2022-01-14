package com.mercadolivro.controllers

import com.fasterxml.jackson.databind.util.BeanUtil
import com.mercadolivro.controllers.request.PostCustomerRequest
import com.mercadolivro.controllers.request.PutCustomerRequest
import com.mercadolivro.extension.toCustomerModel
import com.mercadolivro.models.CustomerModel
import com.mercadolivro.services.CustomerService
import org.springframework.beans.BeanUtils
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customers")
class CustomerController(
    val customerService : CustomerService
) {

    @GetMapping
    fun getAll(@RequestParam name: String?): List<CustomerModel> {
        return customerService.getAll(name)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody customer: PostCustomerRequest) {
        customerService.create(customer.toCustomerModel())
    }

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: Int): CustomerModel {
        return customerService.getCustomerById(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody customer: PutCustomerRequest) {
        customerService.update(customer.toCustomerModel(id))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        customerService.delete(id)
    }

}