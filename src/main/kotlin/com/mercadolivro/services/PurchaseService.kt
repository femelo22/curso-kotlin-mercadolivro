package com.mercadolivro.services

import com.mercadolivro.models.PurchaseModel
import com.mercadolivro.repositories.PurchaseRepository
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository
){

    fun create(purchaseModel: PurchaseModel){
        purchaseRepository.save(purchaseModel)
    }

}
