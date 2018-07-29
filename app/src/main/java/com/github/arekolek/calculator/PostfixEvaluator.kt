package com.github.arekolek.calculator

import java.math.BigDecimal
import java.math.MathContext
import java.util.*

private const val ADD = "+"
private const val SUB = "-"
private const val MUL = "*"
private const val DIV = "/"

class PostfixEvaluator {

    fun evaluate(tokens: List<String>): BigDecimal {
        val stack = Stack<BigDecimal>()

        for (token in tokens) {
            when (token) {
                ADD -> stack.reduceWith(BigDecimal::plus)
                SUB -> stack.reduceWith(BigDecimal::minus)
                MUL -> stack.reduceWith(BigDecimal::multiply)
                DIV -> stack.reduceWith { a, b -> a.divide(b, MathContext.DECIMAL128) }
                else -> stack.push(token.toBigDecimal())
            }
        }

        return stack.single()
    }

    private fun Stack<BigDecimal>.reduceWith(operator: (BigDecimal, BigDecimal) -> BigDecimal) {
        val b = pop()
        val a = pop()
        push(operator(a, b))
    }

}