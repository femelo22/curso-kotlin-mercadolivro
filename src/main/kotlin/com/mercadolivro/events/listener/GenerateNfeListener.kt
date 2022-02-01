package com.mercadolivro.events.listener

import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.services.PurchaseService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.*

@Component
class GenerateNfeListener(
    private val purchaseService: PurchaseService
) {

    //Sempre que o evento de compra de livro for disparado, essa função irá ouvir, e excecutar
    @Async
    @EventListener
    fun listen(purchaseEvent: PurchaseEvent) {
        println("Gerando NFE")
        val nfe = UUID.randomUUID().toString()
        val purchaseModel = purchaseEvent.purchaseModel.copy(nfe = nfe) // Cria uma copia da nossa compra
        purchaseService.update(purchaseModel)
    }
}