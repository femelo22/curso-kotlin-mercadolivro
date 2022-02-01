package com.mercadolivro.events.listener

import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.services.BookService
import com.mercadolivro.services.PurchaseService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.*

@Component
class UpdateSoldBookListener(
    private val bookService: BookService
) {

    @Async
    @EventListener
    fun listen(purchaseEvent: PurchaseEvent) {
        println("Atualizando estado dos livros")
        bookService.purchase(purchaseEvent.purchaseModel.books)
    }
}