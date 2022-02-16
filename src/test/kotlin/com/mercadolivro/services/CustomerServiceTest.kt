package com.mercadolivro.services

import com.mercadolivro.enuns.CustomerStatus
import com.mercadolivro.enuns.Profile
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.models.CustomerModel
import com.mercadolivro.repositories.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var bookService: BookService

    @MockK
    private lateinit var bCrypt: BCryptPasswordEncoder

    @InjectMockKs
    private lateinit var customerService: CustomerService

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

    @Test
    fun `should return all customers`() {
        val fakeCustomers = listOf(buildCustomer(), buildCustomer())

        every { customerRepository.findAll() } returns fakeCustomers

        val customers = customerService.getAll()

        Assertions.assertEquals(fakeCustomers, customers)

        verify(exactly = 1) { customerRepository.findAll() } //garante que foi chamado uma unica vez
    }

    @Test
    fun `should create customer and encrypt password test`() {
        val initialPassword = Random().nextInt().toString()
        val fakeCustomer = buildCustomer(password = initialPassword)
        val fakePassword = UUID.randomUUID().toString()
        val fakeCustomerEncrypted = fakeCustomer.copy(password = fakePassword)

        every { customerRepository.save(fakeCustomerEncrypted) } returns fakeCustomer
        every { bCrypt.encode(initialPassword) } returns fakePassword

        customerService.create(fakeCustomer)

        verify(exactly = 1) { customerRepository.save(fakeCustomerEncrypted) }
        verify(exactly = 1) { bCrypt.encode(initialPassword) }
    }

    @Test
    fun `should return costumer by id`() {
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)

        every { customerRepository.findById(id) } returns Optional.of(fakeCustomer)

        val customer = customerService.getCustomerById(id)

        Assertions.assertEquals(fakeCustomer, customer)
        verify(exactly = 1) { customerRepository.findById(id) }
    }

    @Test
    fun `should throw error when find customer by id not found`() {
        val id = Random().nextInt()

        every { customerRepository.findById(id) } returns Optional.empty()

        val error = assertThrows<NotFoundException> {
            customerService.getCustomerById(id)
        }

        Assertions.assertEquals("Customer [$id] not exists", error.message)
        Assertions.assertEquals("ML-002", error.errorCode)
        verify(exactly = 1) { customerRepository.findById(id) }
    }

}