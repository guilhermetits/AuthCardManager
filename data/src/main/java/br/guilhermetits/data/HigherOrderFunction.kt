package br.guilhermetits.data

import java.lang.Math.pow

// Algumas formas de se escrever um callback
typealias Printer = String.() -> Unit

typealias Callback = ((Number) -> Unit)?
typealias LazyCalculator = (Operation, Callback) -> Unit
typealias Parser = (String) -> Int
typealias ParserUnparser = (String) -> (Int) -> (String) -> (Int) -> String

sealed class Operation
data class Sum(val fisrtVal: Int, val secondVal: Int) : Operation()
data class Multiply(val firstVal: Int, val secondVal: Int) : Operation()
data class Divide(val numerator: Int, val denominator: Int) : Operation()
data class Raise(val number: Int, val exponent: Int) : Operation()

fun getOperator(operation: Operation, callback: Callback) {
    return when (operation) {
        is Sum -> {
            callback?.invoke(operation.fisrtVal + operation.secondVal) ?: Unit
        }
        is Multiply -> {
            callback?.invoke(operation.firstVal * operation.secondVal) ?: Unit
        }
        is Divide -> {
            callback?.invoke(operation.numerator / operation.denominator) ?: Unit
        }
        is Raise -> {
            callback?.invoke(pow(operation.number.toDouble(), operation.exponent.toDouble()))
                    ?: Unit
        }
    }
}

val printer: Printer = { println(this) }
// method association
val lazyCalculator: LazyCalculator = ::getOperator
var parser: Parser = fun(text: String): Int {
    return text.toInt()
}
val parserOption: Parser = { text: String ->
    val uppertext = text.toUpperCase()
    uppertext.toInt()
}
val parserUnparser: ParserUnparser = { str -> { integer -> { str2 -> { integer2 -> integer2.plus(10).toString() } } } }


class MyTestClass(private val printer: Printer, private val lazyCalculator: LazyCalculator) {
    fun calculateAndPrint(op: Operation, calculatorListener: (Number) -> Unit) {
        lazyCalculator(op) {
            it.toString().printer()
            calculatorListener(it)
        }
    }
}

fun main(vararg args: String) {
    MyTestClass(printer, lazyCalculator)
            //CLOSURE Callback
            .calculateAndPrint(Raise(parser("2"), 10)) {
                parser(it.toString())
            }

    var result1 = parserUnparser("teste")
    val result2 = result1(10)
    val result3 = result2("teste")
    val result4 = result3(100)
    val finalResult = result4 == "110"
}
