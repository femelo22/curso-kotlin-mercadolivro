package com.mercadolivro.repositories

import com.mercadolivro.models.CustomerModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CustomerRepository: JpaRepository<CustomerModel, Int>{
}