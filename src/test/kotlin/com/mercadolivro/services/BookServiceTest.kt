package com.mercadolivro.services

import com.mercadolivro.enuns.BookStatus
import com.mercadolivro.enuns.CustomerStatus
import com.mercadolivro.enuns.Profile
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.models.BookModel
import com.mercadolivro.models.CustomerModel
import com.mercadolivro.repositories.BookRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockKExtension::class)
class BookServiceTest{

    @MockK
    private lateinit var bookRepository: BookRepository

    @InjectMockKs
    @SpyK
    private lateinit var bookService: BookService

    fun buildCustomer(
        id: Int? = null,
        name: String = "customer name",
        email: String = "${UUID.randomUUID()}@gmail.com",
        password: String = "password"
    ) = CustomerModel(
        id = id,
        name = name,
        email = email,
        status = CustomerStatus.ATIVO,
        password = password,
        roles = setOf(Profile.CUSTOMER)
    )

    fun buildBook(
        id: Int? = null,
        name: String = "Ajin",
        price: BigDecimal = 20.toBigDecimal(),
    ) = BookModel(
        id = id,
        name = name,
        price = price,
        customer = buildCustomer(),
        status = BookStatus.ATIVO
    )


    @Test
    fun `should return all books`() {
        val fakeBooks = listOf(buildBook(), buildBook())

        every { bookRepository.findAll() } returns fakeBooks

        val books = bookService.findAll()

        Assertions.assertEquals(fakeBooks, books)

        verify(exactly = 1) { bookRepository.findAll() }
    }

    @Test
    fun `should return book by id`(){
        val id = Random().nextInt()
        val fakeBook = buildBook(id = id)

        every { bookRepository.findById(id) } returns Optional.of(fakeBook)

        val book = bookService.findById(id)

        Assertions.assertEquals(fakeBook, book)
        verify(exactly = 1) { bookRepository.findById(id) }

    }

    @Test
    fun `should throw error when find book by id`() {
        val id = Random().nextInt()

        every { bookRepository.findById(id) } returns Optional.empty()

        val error = assertThrows<NotFoundException> {
            bookService.findById(id)
        }

        Assertions.assertEquals("Book [$id] not exists", error.message)
        Assertions.assertEquals("ML-001", error.errorCode)

        verify(exactly = 1){ bookRepository.findById(id) }
    }

    @Test
    fun `should create new book`() {
        val fakeBook = buildBook()

        every { bookRepository.save(fakeBook) } returns fakeBook

        bookService.create(fakeBook)

        verify(exactly = 1) { bookRepository.save(fakeBook) }
    }

    @Test
    fun `should update book`() {
        val id = Random().nextInt()
        val fakeBook = buildBook(id = id)

        every { bookRepository.existsById(id) } returns true
        every { bookRepository.save(fakeBook) } returns fakeBook

        bookService.update(fakeBook)

        verify(exactly = 1) { bookRepository.existsById(id) }
        verify(exactly = 1) { bookRepository.save(fakeBook) }
    }

    @Test
    fun `should throw error when update book`() {
        val id = Random().nextInt()
        val fakeBook = buildBook(id = id)

        every { bookRepository.existsById(id) } returns false
        every { bookRepository.save(fakeBook) } returns fakeBook

        val error = assertThrows<NotFoundException> {
            bookService.update(fakeBook)
        }

        Assertions.assertEquals("Book [$id] not exists", error.message)
        Assertions.assertEquals("ML-001", error.errorCode)

        verify(exactly = 1) { bookRepository.existsById(id) }
        verify(exactly = 0) { bookRepository.save(fakeBook) }
    }

//    @Test
//    fun `should delete book by id`() {
//        val id = Random().nextInt()
//        val fakeBook = buildBook(id = id)
//
//        every { bookRepository.findById(id) } returns Optional.of(fakeBook)
//        every { bookRepository.save(fakeBook) } returns fakeBook
//
//        bookService.delete(id)
//
//        verify(exactly = 1) { bookRepository.findById(id) }
//        verify(exactly = 1) { bookRepository.save(fakeBook) }
//    }

//    @Test
//    fun `should delete book by customer`() {
//
//    }

    @Test
    fun `should find all books by status active`() {
        val fakeBooks = listOf(buildBook(), buildBook())

        every { bookRepository.findAllByStatus(BookStatus.ATIVO) } returns fakeBooks

        val books = bookService.findAllByStatus()

        verify(exactly = 1) { bookRepository.findAllByStatus(BookStatus.ATIVO) }
        Assertions.assertEquals(fakeBooks, books)
    }

}