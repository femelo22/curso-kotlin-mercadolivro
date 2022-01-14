package com.mercadolivro.controllers

import com.mercadolivro.controllers.request.PostBookRequest
import com.mercadolivro.extension.toBookModel
import com.mercadolivro.services.BookService
import com.mercadolivro.services.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.net.http.HttpRequest

@RestController
@RequestMapping("/books")
class BookController(
    val customerService: CustomerService,
    val bookService: BookService

){

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: PostBookRequest){
        val customer = customerService.getCustomerById(request.customerId)
        bookService.create(request.toBookModel(customer))
    }
}