package com.mercadolivro.services

import com.mercadolivro.models.BookModel
import com.mercadolivro.repositories.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
){

    fun create(book: BookModel) {

        bookRepository.save(book)

    }
}