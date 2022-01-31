package com.mercadolivro.repositories

import com.mercadolivro.models.PurchaseModel
import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseRepository: JpaRepository<PurchaseModel, Int> {

}