package com.mercadolivro.services

import com.mercadolivro.controllers.request.PostCustomerRequest
import com.mercadolivro.controllers.request.PutCustomerRequest
import com.mercadolivro.models.CustomerModel
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@Service
class CustomerService {

    //"Mockando" um objeto
    val customers = mutableListOf<CustomerModel>()

    fun getAll(name: String?): List<CustomerModel> {
        name?.let {
            return customers.filter { it.name.contains(name, true) }
        }
        return customers
    }

    fun getCustomer(id: Int): CustomerModel{
        return customers.filter { it.id == id }.first()
    }

    fun create(customer: PostCustomerRequest){
        val id = if(customers.isEmpty()){
            1
        }else {
            customers.last().id + 1;
        }
        customers.add(CustomerModel(id, customer.name, customer.email))
        println(customer)
    }

    fun update(id: Int, customer: PutCustomerRequest){
        return customers.filter { it.id == id }.first().let {
            it.name = customer.name
            it.email = customer.email
        }
    }

    fun delete(id: Int){
        customers.removeIf { it.id == id }
    }

}