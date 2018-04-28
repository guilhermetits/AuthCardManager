package br.guilhermetits.data

import java.lang.Math.pow

typealias Printer = (String) -> Unit
typealias LazyCalculator = (Operation, (Number) -> Unit) -> Unit

fun getOperator(operation: Operation, callback: (Number) -> Unit) {
    return when (operation) {
        is Sum -> {
            callback(operation.fisrtVal + operation.secondVal)
        }
        is Multiply -> {
            callback(operation.firstVal * operation.secondVal)
        }
        is Divide -> {
            callback(operation.numerator / operation.denominator)
        }
        is Raise -> {
            callback(pow(operation.number.toDouble(), operation.exponent.toDouble()))
        }
    }
}

sealed class Operation
data class Sum(val fisrtVal: Int, val secondVal: Int) : Operation()
data class Multiply(val firstVal: Int, val secondVal: Int) : Operation()
data class Divide(val numerator: Int, val denominator: Int) : Operation()
data class Raise(val number: Int, val exponent: Int) : Operation()

val printer: Printer = { println(it) }
 // method association
val lazyCalculator: LazyCalculator = ::getOperator
val parser = fun(text:String): Int {
    return Integer.parseInt(text)
}

fun main(vararg args: String) {
    MyTestClass(printer, lazyCalculator)
            .calculateAndPrint(Raise(parser("2"),10))
}

class MyTestClass(private val printer: Printer, private val lazyCalculator: LazyCalculator) {
    //CLOSURE Callback
    fun calculateAndPrint(op: Operation) {
        lazyCalculator(op) {
            printer(it.toString())
        }
    }
}