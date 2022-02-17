package com.mercadolivro.services

import com.mercadolivro.repositories.PurchaseRepository
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
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

}