package com.mercadolivro.config

import com.mercadolivro.enuns.Profile
import com.mercadolivro.repositories.CustomerRepository
import com.mercadolivro.security.AuthenticationFilter
import com.mercadolivro.security.AuthorizationFilter
import com.mercadolivro.security.CustomAuthenticationEntryPoint
import com.mercadolivro.security.JwtUtil
import com.mercadolivro.services.UserDetailCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val customerRepository: CustomerRepository,
    private val userDetails: UserDetailCustomService,
    private val jwtUtil: JwtUtil,
    private val customEntryPoint: CustomAuthenticationEntryPoint
): WebSecurityConfigurerAdapter() {

    private val PUBLIC_POST_MATCHERS = arrayOf(
        "/customer"
    )

    private val ADMIN_MATCHERS = arrayOf(
            "/admin/**"
    )

    override fun configure(auth: AuthenticationManagerBuilder) {
            auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        //desabilitando cors
        http.cors().and().csrf().disable()

        //todas as req que chegarem, devem estar autenticadas
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll() //o caracter "*" trasforma nossa lista para string, como se estivessemos passando varias strings
                .antMatchers(*ADMIN_MATCHERS).hasAuthority(Profile.ADMIN.description)
            .anyRequest().authenticated()

        //Passa a req para nossa classe de filtro de requisi????es
        http.addFilter(AuthenticationFilter(authenticationManager(), customerRepository, jwtUtil))

        //Filtro para ver se tem autoriza????o para fazer a requisi????o (vendo as roles)
        http.addFilter(AuthorizationFilter(authenticationManager(), userDetails, jwtUtil))

        //deixar as requisi????es independentes, a req que chegar, n??o tem a ver com a ultima req que chegou
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.exceptionHandling().authenticationEntryPoint(customEntryPoint)
    }

    //Liberar o Swagger
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "swagger-resources/**", "configuration/**", "/swagger-ui.html", "/webjars/**")
    }

    //Liberar os cors para aplica????o
    @Bean
    fun corsConfig(): CorsFilter{
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    @Bean
     fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
         return BCryptPasswordEncoder()
     }

}