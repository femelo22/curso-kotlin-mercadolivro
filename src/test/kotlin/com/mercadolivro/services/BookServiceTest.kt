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

}