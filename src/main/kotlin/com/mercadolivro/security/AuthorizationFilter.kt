package com.mercadolivro.security

import com.mercadolivro.exception.AuthenticationException
import com.mercadolivro.services.UserDetailCustomService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(
        authenticationManager: AuthenticationManager,
        private val userDetails: UserDetailCustomService,
        private val jwtUtil: JwtUtil
): BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        //Recuperar no token
        val authorization = request.getHeader("Authorization")
        if(authorization != null && authorization.startsWith("Bearer")){
            val auth = getAuthentication(authorization.split(" ")[1])
            SecurityContextHolder.getContext().authentication = auth
        }

        //Valida se o usuário consegue acessar a URL que está chamando
        chain.doFilter(request, response)

    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        if(!jwtUtil.isValidToken(token)){
            throw AuthenticationException("Token inválido", "999")
        }
        val subject = jwtUtil.getSubject(token)
        val customer = userDetails.loadUserByUsername(subject)
        return UsernamePasswordAuthenticationToken(subject, null, customer.authorities)

    }
}