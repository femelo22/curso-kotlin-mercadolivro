package com.mercadolivro.enuns

enum class Errors(val code: String, val message: String) {

    ML000("ML-000", "Access Denied"),
    ML001("ML-001", "Book [%s] not exists"),
    ML002("ML-002", "Customer [%s] not exists"),
    ML003("ML-003", "Cannot update book with status [%s]"),
    ML004("ML-004", "Invalid Request"),
    ML005("ML-005", "Cannot purchase book with status [%s]")

}