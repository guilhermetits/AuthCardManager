package br.guilhermetits.data

import java.lang.Math.pow

// ALGUMAS FORMAS DE SE DECLARAR UM LAMBDA

// Padrão
typealias Parser = (String) -> Int

// Padrão com 2 parametros
typealias LazyCalculator = (op: Operation, callback: Callback) -> Unit

// Como extension de um tipo
typealias Printer = String.() -> Unit

// Um lambda nullable
typealias Callback = ((Number) -> Unit)?

// Ordenação direita para a esqueda
typealias ParserUnparser = (String) -> (Int) -> (String) -> (Int) -> String

sealed class Operation
data class Sum(val fisrtVal: Int, val secondVal: Int) : Operation()
data class Multiply(val firstVal: Int, val secondVal: Int) : Operation()
data class Divide(val numerator: Int, val denominator: Int) : Operation()
data class Raise(val number: Int, val exponent: Int) : Operation()

// Um método que atende ao tipo LazyCalculator
fun getOperator(operation: Operation, callback: Callback) {
     when (operation) {
        is Sum -> {
            callback?.invoke(operation.fisrtVal + operation.secondVal)
        }
        is Multiply -> {
            callback?.invoke(operation.firstVal * operation.secondVal)
        }
        is Divide -> {
            callback?.invoke(operation.numerator / operation.denominator)
        }
        is Raise -> {
            callback?.invoke(pow(operation.number.toDouble(), operation.exponent.toDouble()))
        }
    }
}

// declaração de um lambda extension
// uso do this em vez do it como parametro implicito
val printer: Printer = { println(this) }
// declaração de um lambda pela
val lazyCalculator: LazyCalculator = ::getOperator
var parser: Parser = fun(text: String): Int {
    return text.toInt()
}
//Declaração de um lambda com varaias linhas
//a ultima linha é o return
val parserOption: Parser = { text ->
    val uppertext = text.toUpperCase()
    uppertext.toInt()
}
// recursão de lambdas
val parserUnparser: ParserUnparser = { str -> { integer -> { str2 -> { integer2 -> integer2.plus(10).toString() } } } }


class MyTestClass(private val printer: Printer, private val lazyCalculator: LazyCalculator) {
    fun calculateAndPrint(op: Operation, calculatorListener: (Number) -> Unit) {
        // Chamada de um lambda como parametro de um closure
        lazyCalculator(op) {
            it.toString().printer()
            calculatorListener(it)
        }
    }
}

fun main(vararg args: String) {
    MyTestClass(printer, lazyCalculator)
            // Chamada de um método sem o closure
            .calculateAndPrint(Raise(parser("2"), 10), { parser(it.toString()) })

    // Recursão de lambdas
    var result1 = parserUnparser("teste")
    val result2 = result1(10)
    val result3 = result2("teste")
    val result4 = result3(100)
    val finalResult = result4 == "110"
}
