package com.mercadolivro.repositories

import com.mercadolivro.enuns.BookStatus
import com.mercadolivro.models.BookModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: JpaRepository<BookModel, Int> {

     fun findAllByStatus(status: BookStatus): List<BookModel>

}