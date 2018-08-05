package com.github.arekolek.calculator.math

import java.util.*

private const val ADD = "+"
private const val SUB = "-"
private const val MUL = "*"
private const val DIV = "/"
private const val LEFT_PAREN = "("
private const val RIGHT_PAREN = ")"

class NotationConverter {

    fun toPostfix(tokens: Iterable<String>): List<String> {
        val stack = Stack<String>()
        val output = mutableListOf<String>()

        for (token in tokens) {
            when (token) {
                ADD, SUB, MUL, DIV, LEFT_PAREN -> {
                    output += stack.popWhilePrecedenceHigherThan(token)
                    stack.push(token)
                }
                RIGHT_PAREN -> {
                    output += stack.popWhilePrecedenceHigherThan(token)
                    stack.pop()
                }
                else -> output += token
            }
        }

        while (stack.isNotEmpty()) {
            output += stack.pop()
        }
        return output
    }

    private fun Stack<String>.popWhilePrecedenceHigherThan(token: String): List<String> {
        fun higherPrecedence() = isNotEmpty() && when (token) {
            RIGHT_PAREN -> peek() != LEFT_PAREN
            ADD -> peek() in setOf(ADD, SUB, MUL, DIV)
            SUB -> peek() in setOf(ADD, SUB, MUL, DIV)
            MUL -> peek() in setOf(MUL, DIV)
            DIV -> peek() in setOf(MUL, DIV)
            else -> false
        }

        val operators = mutableListOf<String>()
        while (higherPrecedence()) {
            operators += pop()
        }
        return operators
    }

}
