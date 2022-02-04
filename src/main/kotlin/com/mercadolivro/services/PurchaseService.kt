package com.mercadolivro.services

import com.mercadolivro.enuns.BookStatus
import com.mercadolivro.enuns.Errors
import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.exception.BadRequestException
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
        purchaseModel.books.forEach {
            if (it.status == BookStatus.DELETADO || it.status == BookStatus.VENDIDO){
                throw BadRequestException(Errors.ML005.message.format(it.status), Errors.ML005.code);
            }
        }
        println("Disparando evento de compra")
        purchaseRepository.save(purchaseModel)
        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseModel))
        println("Finalizando processo de compra")
    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)
    }

    fun getAllPurchaseByCustomer(customerId: Int): List<PurchaseModel> {
        return purchaseRepository.findAllByCustomerId(customerId)
    }

}
