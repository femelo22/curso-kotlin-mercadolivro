package com.mercadolivro.repository

import com.mercadolivro.helper.buildCustomer
import com.mercadolivro.repositories.CustomerRepository
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerRepositoryTest {

    @Autowired
    private lateinit var customerRepository: CustomerRepository


    @BeforeEach
    fun setup() = customerRepository.deleteAll()

    @Test
    fun `should return name containing`(){
        val marcos = customerRepository.save(buildCustomer(name = "Marcos"))
        val marcelo = customerRepository.save(buildCustomer(name = "Marcelo"))
        customerRepository.save(buildCustomer(name = "Alex"))

        val customers = customerRepository.findByNameContaining("Ma")

        assertEquals(listOf(marcos, marcelo), customers)
    }

    @Nested
    inner class `exists by email`() {

        @Test
        fun `should return true when email exists`() {
            val email = "email@teste.com"
            customerRepository.save(buildCustomer(email = email))

            val existsByEmail = customerRepository.existsByEmail(email)

            assertTrue(existsByEmail)
        }

        @Test
        fun `should return false when email do not exists`() {
            val email = "emailnotexists@teste.com"

            val notExistsByEmail = customerRepository.existsByEmail(email)

            assertFalse(notExistsByEmail)
        }

    }

}