package com.mercadolivro.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthotizationFilter(
        authenticationManager: AuthenticationManager,
        private val jwtUtil: JwtUtil
): BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        //Recuperar no token
        val authorization = request.getHeader("Authorization")
        if(authorization != null && authorization.startsWith("Bearer")){
            getAuthentication(authorization.split(" ")[1])
        }

    }

    private fun getAuthentication(token: String) {
        if(jwtUtil.isValidToken(token)){

        }
    }
}