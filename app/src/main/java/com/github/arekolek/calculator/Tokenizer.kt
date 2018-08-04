package com.github.arekolek.calculator

class Tokenizer {

    fun tokenize(expression: String): List<String> {
        val tokens = mutableListOf<String>()
        var number = ""

        for (character in expression) {
            when (character) {
                in ".0123456789" -> number += character
                in "+-*/()" -> {
                    if (number.isNotEmpty()) {
                        tokens += number.validate()
                        number = ""
                    }
                    tokens += character.toString()
                }
            }
        }

        if (number.isNotEmpty()) {
            tokens += number.validate()
        }

        return tokens
    }

    private fun String.validate() = apply { checkNotNull(toBigDecimalOrNull()) }

}
