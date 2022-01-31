package com.mercadolivro.services

import com.mercadolivro.models.PurchaseModel
import com.mercadolivro.repositories.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
){

    fun create(purchaseModel: PurchaseModel){
        purchaseRepository.save(purchaseModel)
    }

}
