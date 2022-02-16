package com.mercadolivro.services

import com.mercadolivro.controllers.request.PostCustomerRequest
import com.mercadolivro.controllers.request.PutCustomerRequest
import com.mercadolivro.enuns.CustomerStatus
import com.mercadolivro.enuns.Errors
import com.mercadolivro.enuns.Profile
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.models.CustomerModel
import com.mercadolivro.repositories.CustomerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@Service
class CustomerService (
    private val repo: CustomerRepository,
    private val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder
){

    fun getAll(): List<CustomerModel> {
        return repo.findAll()
    }

    fun create(customer: CustomerModel) {
        val customerCopy = customer.copy(
            roles = setOf(Profile.CUSTOMER),
            password = bCrypt.encode(customer.password)
        )
       repo.save(customerCopy)
    }

    fun getCustomerById(id: Int): CustomerModel {
        return repo.findById(id).orElseThrow { NotFoundException(Errors.ML002.message.format(id), Errors.ML002.code) }
    }

    fun update(customer: CustomerModel) {

        if(!repo.existsById(customer.id!!)){
           throw NotFoundException(Errors.ML002.message.format(customer.id), Errors.ML002.code)
        }

        repo.save(customer)
    }

    fun delete(id: Int) {
        val customer = getCustomerById(id)

        bookService.deleteByCustomer(customer)

        customer.status = CustomerStatus.INATIVO

        repo.save(customer)
    }

    fun emailAvailable(email: String): Boolean {
        return !repo.existsByEmail(email)
    }

}