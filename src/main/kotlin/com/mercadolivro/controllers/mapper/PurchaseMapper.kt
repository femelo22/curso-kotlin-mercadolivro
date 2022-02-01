package com.mercadolivro.controllers.mapper

import com.mercadolivro.controllers.request.PostPurchaseRequest
import com.mercadolivro.models.PurchaseModel
import com.mercadolivro.services.BookService
import com.mercadolivro.services.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper (
    private val bookService: BookService,
    private val customerService: CustomerService
){

    fun toModel(request: PostPurchaseRequest): PurchaseModel {
        val customer = customerService.getCustomerById(request.customerId)
        val books = bookService.findAllByIds(request.booksId)

        return PurchaseModel(
            customer = customer,
            books = books.toMutableList(),
            price = books.sumOf { it.price },
        )
    }

}