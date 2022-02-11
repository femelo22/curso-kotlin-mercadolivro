package com.mercadolivro.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mercadolivro.controllers.request.LoginRequest
import com.mercadolivro.exception.AuthenticationException
import com.mercadolivro.repositories.CustomerRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//essa classe serve para moficiar a rota que o proprio spring gera "/login"

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val customerRepository: CustomerRepository,
    private val jwtUtil: JwtUtil
): UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try{
            //vamos pegar nossa request e trasformar o que vem no body dela, para nosso objeto de login "loginRequest"
            val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequest::class.java)

            //vamos recuparar o ID do cliente atráves do email que chegou na req
            val id = customerRepository.findByEmail(loginRequest.email)?.id

            //estamos validando nosso usuario e passando ele para autenticação
            val authToken = UsernamePasswordAuthenticationToken(id, loginRequest.password)

            return authenticationManager.authenticate(authToken)
        }catch (ex: Exception) {
            throw AuthenticationException("Falha ao autenticar", "999")
        }
    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {

        val id = (authResult.principal as UserCustomDatails).id

        val token = jwtUtil.generateToken(id)

        response.addHeader("Authorization", "Bearer $token")

    }
}