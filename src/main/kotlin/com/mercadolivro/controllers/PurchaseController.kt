package com.mercadolivro.controllers

import com.mercadolivro.controllers.mapper.PurchaseMapper
import com.mercadolivro.controllers.request.PostPurchaseRequest
import com.mercadolivro.models.PurchaseModel
import com.mercadolivro.services.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/purchase")
class PurchaseController(
    private val purchaseService: PurchaseService,
    private val purchaseMapper: PurchaseMapper
){


    @GetMapping("/customer/{customerId}")
    fun getAllPurchaseByCustomer(@PathVariable customerId: Int): List<PurchaseModel> {
        return purchaseService.getAllPurchaseByCustomer(customerId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun purchase(@RequestBody request: PostPurchaseRequest){
        purchaseService.create(purchaseMapper.toModel(request))
    }

}