package com.mercadolivro.services

import com.mercadolivro.enuns.BookStatus
import com.mercadolivro.enuns.Errors
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.models.BookModel
import com.mercadolivro.models.CustomerModel
import com.mercadolivro.repositories.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
){

    fun findAll(): List<BookModel> {
        return bookRepository.findAll()
    }

    fun findById(id: Int): BookModel {
        return bookRepository.findById(id).orElseThrow{NotFoundException(Errors.ML001.message.format(id), Errors.ML001.code)}
    }

    fun create(book: BookModel) {
        bookRepository.save(book)
    }

    fun update(book: BookModel) {
        if(!bookRepository.existsById(book.id!!)){
            throw NotFoundException(Errors.ML001.message.format(book.id), Errors.ML001.code)
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

    fun findAllByIds(booksId: Set<Int>): List<BookModel> {
        return bookRepository.findAllById(booksId).toList()
    }

    fun purchase(books: MutableList<BookModel>) {
        books.map {
            it.status = BookStatus.VENDIDO
        }
        bookRepository.saveAll(books)
    }
}