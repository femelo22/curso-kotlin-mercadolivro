package com.mercadolivro.services

import java.util.*

var clienteBanco = Cliente("Luiz", 0.0)

fun main(){
    val scan = Scanner(System.`in`)

    println("1) Deposito")
    println("2) Saque")
    println("3) Transferencia")
    println("4) Extrato")
    println("5) Sair")

    println("Digite uma opção")

    var choice = scan.nextInt()

    when(choice){
        1 -> depositar()
        else -> "Invalido"
    }
}


fun depositar(){
    val scan = Scanner(System.`in`)

    println("Digite o valor do depósito")

    var novoSaldo = scan.nextInt() + clienteBanco.saldoAtual;
    println("Seu antigo saldo: R$ ${clienteBanco.saldoAtual} ")

    clienteBanco.saldoAtual = novoSaldo

    println("Seu saldo atual: R$ ${clienteBanco.saldoAtual}")
}

data class Cliente(var name: String, var saldoAtual: Double){

}