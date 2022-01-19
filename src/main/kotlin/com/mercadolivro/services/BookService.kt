package com.mercadolivro.services

import com.mercadolivro.enuns.BookStatus
import com.mercadolivro.models.BookModel
import com.mercadolivro.models.CustomerModel
import com.mercadolivro.repositories.BookRepository
import org.springframework.stereotype.Service
import java.awt.print.Book

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
        val book = findById(id)
        book.status = BookStatus.DELETADO
        update(book)
    }

    fun findAllByStatus(): List<BookModel> {
        return bookRepository.findAllByStatus(BookStatus.ATIVO)
    }

    fun deleteByCustomer(customer: CustomerModel) {
        val books = bookRepository.findByCustomer(customer)
        for(book in books){
            book.status = BookStatus.DELETADO
        }
        bookRepository.saveAll(books)
    }
}