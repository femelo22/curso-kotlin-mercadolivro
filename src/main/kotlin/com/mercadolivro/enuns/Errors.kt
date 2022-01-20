package com.mercadolivro.enuns

enum class Errors(val code: String, val message: String) {

    ML001("ML-001", "Book [%s] not exists"),
    ML002("ML-002", "Customer [%s] not exists")

}