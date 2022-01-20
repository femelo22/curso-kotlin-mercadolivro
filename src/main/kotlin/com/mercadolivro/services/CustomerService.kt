package com.mercadolivro.services

import com.mercadolivro.controllers.request.PostCustomerRequest
import com.mercadolivro.controllers.request.PutCustomerRequest
import com.mercadolivro.enuns.CustomerStatus
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.models.CustomerModel
import com.mercadolivro.repositories.CustomerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@Service
class CustomerService (
    val repo: CustomerRepository,
    val bookService: BookService
){

    fun getAll(name: String?): List<CustomerModel> {
        name?.let {
            return repo.findByName(it)
        }

        return repo.findAll()
    }

    fun create(customer: CustomerModel) {
       repo.save(customer)
    }

    fun getCustomerById(id: Int): CustomerModel {
        return repo.findById(id).orElseThrow { NotFoundException("Customer [${id}] not exists", "ML-002") }
    }

    fun update(customer: CustomerModel) {

        if(!repo.existsById(customer.id!!)){
            throw Exception()
        }

        repo.save(customer)
    }

    fun delete(id: Int) {
        val customer = getCustomerById(id)

        bookService.deleteByCustomer(customer)

        customer.status = CustomerStatus.INATIVO

        repo.save(customer)
    }

}