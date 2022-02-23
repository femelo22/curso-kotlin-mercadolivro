package com.mercadolivro.services

import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.helper.buildPurchase
import com.mercadolivro.repositories.PurchaseRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher

@ExtendWith(MockKExtension::class)
class PurchaseServiceTest{

    @MockK
    private lateinit var purchaseRepository: PurchaseRepository

    @MockK
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @InjectMockKs
    @SpyK
    private lateinit var purchaseService: PurchaseService

    val purchaseEventSlot = slot<PurchaseEvent>()


    @Test
    fun `should create purchase and publish event`() {
        val purchaseFake = buildPurchase()

        every { purchaseRepository.save(purchaseFake) } returns purchaseFake

        every { applicationEventPublisher.publishEvent(any()) } just runs

        purchaseService.create(purchaseFake)

        verify(exactly = 1) { purchaseRepository.save(purchaseFake) }

        verify { applicationEventPublisher.publishEvent(capture(purchaseEventSlot)) }

        Assertions.assertEquals(purchaseFake, purchaseEventSlot.captured.purchaseModel)
    }

    @Test
    fun `should update purchase`() {
        val purchaseFake = buildPurchase()

        every { purchaseRepository.save(purchaseFake) } returns purchaseFake

        purchaseService.update(purchaseFake)

        verify(exactly = 1) { purchaseRepository.save(purchaseFake) }
    }

}