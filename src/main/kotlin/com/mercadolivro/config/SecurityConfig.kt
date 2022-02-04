package com.mercadolivro.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(): WebSecurityConfigurerAdapter() {

    private val PUBLIC_POST_MATCHERS = arrayOf(
        "/customer"
    )

    override fun configure(http: HttpSecurity) {
        //desabilitando cors
        http.cors().and().csrf().disable()

        //todas as req que chegarem, devem estar autenticadas
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll() //o caracter "*" trasforma nossa lista para string, como se estivessemos passando varias strings
            .anyRequest().authenticated()

        //deixar as requisições independentes, a req que chegar, não tem a ver com a ultima req que chegou
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Bean
     fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
         return BCryptPasswordEncoder()
     }

}