package com.mercadolivro.services

import com.mercadolivro.events.PurchaseEvent
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
        println("Disparando evento de compra")
        purchaseRepository.save(purchaseModel)
        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseModel))
        println("Finalizando processo de compra")
    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)
    }

}
