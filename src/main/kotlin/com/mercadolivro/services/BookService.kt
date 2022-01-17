package com.mercadolivro.services

import com.mercadolivro.models.BookModel
import com.mercadolivro.models.CustomerModel
import com.mercadolivro.repositories.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
){

    fun create(book: BookModel) {

        bookRepository.save(book)

    }

    fun findAll(): List<BookModel> {
        return bookRepository.findAll()
    }

    fun findById(id: Int): BookModel {
        return bookRepository.findById(id).orElseThrow()
    }

    fun update(book: BookModel) {

        if(!bookRepository.existsById(book.id!!)){
            throw Exception()
        }

        bookRepository.save(book)
    }

    fun delete(id: Int) {
        if(!bookRepository.existsById(id)){
            throw Exception()
        }

        bookRepository.deleteById(id)
    }
}