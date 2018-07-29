package com.github.arekolek.calculator

class Tokenizer {

    fun tokenize(expression: String): List<String> {
        val tokens = mutableListOf<String>()
        var number = ""

        for (token in expression) {
            when (token) {
                in '0'..'9' -> number += token
                in "+-*/()" -> {
                    if (number.isNotEmpty()) {
                        tokens += number
                        number = ""
                    }
                    tokens += token.toString()
                }
            }
        }

        if (number.isNotEmpty()) {
            tokens += number
        }

        return tokens
    }

}
