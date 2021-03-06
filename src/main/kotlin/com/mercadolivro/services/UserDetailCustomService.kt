package com.mercadolivro.services

import com.mercadolivro.exception.AuthenticationException
import com.mercadolivro.repositories.CustomerRepository
import com.mercadolivro.security.UserCustomDatails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailCustomService(
    private val customerRepository: CustomerRepository
): UserDetailsService {
    override fun loadUserByUsername(id: String): UserDetails {
        val customer = customerRepository.findById(id.toInt())
            .orElseThrow { AuthenticationException("Usuario nao encontrado", "999") }
        return UserCustomDatails(customer)
    }
}