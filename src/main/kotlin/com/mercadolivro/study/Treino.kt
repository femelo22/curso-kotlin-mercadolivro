package com.mercadolivro.study

import com.mercadolivro.study.TreinoModel
import java.util.*

var clienteBanco = Cliente("Luiz", 500.0)

var logs = mutableListOf<TreinoModel>()

fun main(){

    var welcome = "Olá ${clienteBanco.name}, seu saldo atual é de R$ ${clienteBanco.saldoAtual}"

    println(welcome)

    var choice = welcome()

    println("Escolha: ${choice}")

    when(choice){
        1 -> depositar()
        2 -> sacar()
        4 -> extrato()
        else -> "Invalido"
    }

}

fun welcome(): Int{
    println("1) Deposito")
    println("2) Saque")
    println("3) Transferencia")
    println("4) Extrato")
    println("5) Sair")
    println("Digite uma opção")

    val scan = Scanner(System.`in`)

    val choice = scan.nextInt()

    return choice
}


fun depositar(){
    val scan = Scanner(System.`in`)

    println("Digite o valor do depósito")

    var valorDeposito = scan.nextInt()

    var novoSaldo = valorDeposito + clienteBanco.saldoAtual;

    println("Seu antigo saldo: R$ ${clienteBanco.saldoAtual} ")

    clienteBanco.saldoAtual = novoSaldo

    println("Seu saldo atual: R$ ${clienteBanco.saldoAtual}")

    val log = TreinoModel("Deposito realizado com valor de R$ ${valorDeposito}", Date())

    logs.add(log)

    welcome()
}

fun sacar() {
    val scan = Scanner(System.`in`)
    println("Digite o valor do saque")
    var valorSaque = scan.nextDouble()

    clienteBanco.saldoAtual = clienteBanco.saldoAtual - valorSaque

    println("Seu novo saldo é: R$ ${clienteBanco.saldoAtual}")
}



fun extrato() {

    println(logs)

    println("Seu saldo atual é: R$ ${clienteBanco.saldoAtual}")
}

data class Cliente(var name: String, var saldoAtual: Double){

}